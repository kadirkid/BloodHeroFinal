package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import jp.wasabeef.blurry.Blurry;
import osoble.bloodhero.Activities.MainActivity;
import osoble.bloodhero.Models.User;
import osoble.bloodhero.R;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    TextView name, email, bloodType;
    RadioButton publicButton, privateButton;
    ImageView backgroundImage, profilePicture;
    Button update, logout;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference childref;
    private User user;
    private ProgressDialog progressDialog;
    private StorageReference mStorageReference;
    private StorageReference degreeRef;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile, container, false);
        getActivity().setTitle("Profile");

        name = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_email);
        bloodType = view.findViewById(R.id.profile_blood);

        publicButton = view.findViewById(R.id.profile_public);
        privateButton = view.findViewById(R.id.profile_private);

        backgroundImage = view.findViewById(R.id.profile_background_image);
        profilePicture = view.findViewById(R.id.profile_picture);

        update = view.findViewById(R.id.profile_update);
        logout = view.findViewById(R.id.profile_logout);
        update.setOnClickListener(this);
        logout.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        degreeRef = mStorageReference.child("User").child(firebaseUser.getUid()).child("/profile.jpg");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("User");

        Log.i("---------------------", "---------------------");
        Log.i("UserID", firebaseUser.getUid());
        Log.i("---------------------", "---------------------");

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("---------------------", "---------------------");
                Log.i("OnDataChange", "Called!!");
                Log.i("---------------------", "---------------------");

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int count = 0;

                for(DataSnapshot child: children){
                    if(child.getKey().equals(firebaseUser.getUid())){
                        user = child.getValue(User.class);

                        Log.i("---------------------", "---------------------");
                        Log.i(user.getName(), user.getPassword());
                        Log.i(user.getEmail(), user.getBloodType());
                        String status = String.valueOf(user.isPrivacy());
                        Log.i(status, "Status");
                        Log.i("---------------------", "---------------------");

                        name.setText(user.getName());
                        email.setText(user.getEmail());
                        bloodType.setText(user.getBloodType());

                        Glide
                                .with(getActivity())
                                .using(new FirebaseImageLoader())
                                .load(degreeRef)
                                .into(profilePicture);

                        Glide
                                .with(getActivity())
                                .using(new FirebaseImageLoader())
                                .load(degreeRef)
                                .into(backgroundImage);

                        if(backgroundImage != null){
                            blurrImage();
                        }


                        if(user.isPrivacy()){
                            privateButton.toggle();
//                            publicButton.setEnabled(false);
                        }
                        else{
                            publicButton.toggle();
//                            privateButton.setEnabled(false);
                        }
                        break;
                    }
                    Log.i("Count", Integer.toString(count));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void blurrImage() {
        BitmapDrawable drawable = (BitmapDrawable) backgroundImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Blurry
                .with(getContext())
                .from(bitmap)
                .into(backgroundImage);
    }

    public void changeFragment(Fragment f){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.homeframe, f);

        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_update:
                //Do something
                break;

            case R.id.profile_logout:
                auth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
//                getActivity().finish();
                break;

            default:
                break;
        }
    }
}
