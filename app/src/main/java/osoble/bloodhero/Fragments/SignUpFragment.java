package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import osoble.bloodhero.Activities.HomeActivity;
import osoble.bloodhero.Models.User;
import osoble.bloodhero.R;

import static android.app.Activity.RESULT_OK;


public class SignUpFragment extends Fragment implements View.OnClickListener {
    public static final int GALLERY_INTENT = 2;
    private String[] bloodTypes, country;
    private String image;
    private ArrayAdapter<String> adapterB, adapterC;
    private Spinner bloodTypeSpinner, countrySpinner;
    private EditText password, email, confirmPassword, name;
    private CheckBox age;
    private Button signupButton, uploadImage;
    private ImageView haveAccount;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private DatabaseReference childref;
    private User user;
    private ProgressDialog progressDialog;
    private FirebaseUser firebaseUser;
    private StorageReference mStorageReference;
    private StorageReference degreeRef;
    private Uri filePath;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sign_up, container, false);

        name = view.findViewById(R.id.nameSignUp);
        email = view.findViewById(R.id.emailSignUp);
        password = view.findViewById(R.id.passwordSignUp);
        confirmPassword = view.findViewById(R.id.confirmPasswordSignUp);
        age = view.findViewById(R.id.confirmAge);

        bloodTypeSpinner = view.findViewById(R.id.bloodTypeSpinner);
        countrySpinner = view.findViewById(R.id.countrySpinner);

        signupButton = view.findViewById(R.id.signupButton);
        uploadImage = view.findViewById(R.id.upload_image);
        haveAccount = view.findViewById(R.id.haveAccount);

        haveAccount.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("User");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading Picture...");
        setupSpinner();

        signupButton.setOnClickListener(this);

        return view;
    }

    public void setupSpinner() {
        bloodTypes = new String[] {"Select Blood Type", "------------------------------", "O-", "O+", "A+",
                "A-", "B+", "B-", "AB+" , "AB-"};
        adapterB = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                bloodTypes);
        bloodTypeSpinner.setAdapter(adapterB);

        country = new String[] {"Select Region", "------------------------------", "Sharjah", "Dubai", "Abu Dhabi",
                "Ras Al Khaimah", "Umm Al Quwain", "Fujairah", "Ajman"};
        adapterC = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                country);
        countrySpinner.setAdapter(adapterC);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupButton:
                if(check()){
                    signup();
                }
                break;

            case R.id.haveAccount:
                changeFragment(new LoginFragment());
                break;

            case R.id.upload_image:
                showFileChooser();
        }
    }

    private void signup() {
        final String namestr = name.getText().toString().trim();
        final String passwordstr = password.getText().toString().trim();
        final String emailstr = email.getText().toString().trim();
        final String blood = bloodTypeSpinner.getSelectedItem().toString();
        final String country_str = countrySpinner.getSelectedItem().toString();

        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(emailstr, passwordstr)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){

                            user = new User(namestr, passwordstr, emailstr, blood, filePath.toString()
                                    , country_str);
                            addToDatabase(user);
                        }
                        else{
                            Toast.makeText(getContext(), "Sign Up failed " + task.getException()
                                    , Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

    }

    private void addToDatabase(User user) {
        firebaseUser = auth.getCurrentUser();
        uploadFile();
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        childref.child(firebaseUser.getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Snackbar.make(getView(), "Login Successful", Snackbar.LENGTH_SHORT);
                            startActivity(new Intent(getActivity(), HomeActivity.class));
//                            getActivity().finish();
                        }
                        else{
                            Toast.makeText(getContext(), "Failed to add User to the database",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean check() {
        String passwordstr = password.getText().toString().trim();
        String confirmstr = confirmPassword.getText().toString().trim();
        String emailstr = email.getText().toString().trim();

        if(!passwordstr.equals(confirmstr)){
            Toast.makeText(getContext(), "Make sure the passwords match", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        else if(!emailstr.contains("@")){
            Toast.makeText(getContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!age.isChecked()){
            Toast.makeText(getContext(), "Confirm your age by ticking the checkbox",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    public void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent.createChooser(intent, "Select Image"), GALLERY_INTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            Log.i("-------Getting Picture", "Successful----------");
        }
    }

    public void uploadFile(){
        if (filePath != null) {
            progressDialog.setMessage("Uploading Picture...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            degreeRef = mStorageReference.child("User").child(firebaseUser.getUid()).child("/profile.jpg");

            degreeRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Log.i("----------File", "Uploaded------");
                            Log.i("Download Link  ", degreeRef.getDownloadUrl().toString());
                            user.setImage(degreeRef.getDownloadUrl().toString());
                            Toast.makeText(getContext(), "File Uploaded!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage(((int) progress) + "% Uploaded..");
                }
            });
        }else{
            Toast.makeText(getContext(), "Select a file first!", Toast.LENGTH_SHORT).show();
        }
    }



    public void changeFragment(Fragment f){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.mainframe, f);

        fragmentTransaction.commit();
    }
}
