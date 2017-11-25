package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import osoble.bloodhero.Adapters.AppointmentAdapter;
import osoble.bloodhero.Models.Appointment;
import osoble.bloodhero.R;


public class HomeFragment extends Fragment {
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private HorizontalCalendar horizontalCalendar;
    private RecyclerView appointmentRecyclerView;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private Query ref;
    private DividerItemDecoration mDividerItemDecoration;
    private SnapHelper snapHelper;
    private AppointmentAdapter mAdapter;
    private ProgressDialog progress;
    private FirebaseUser firebaseUser;
    private StorageReference mStorageReference;
    private StorageReference degreeRef;
    private ImageView background;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");

        background = view.findViewById(R.id.home_background_image);
        /** end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

/** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.home_calendar)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .build();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String test = sdf.format(startDate.getTime());
        Log.e("Time = ", test);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position){
                Log.i("----------The Date is ", date.toString());
                Log.i("-------The position is ", Integer.toString(position));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Log.i("-------", sdf.format(date));
            }
        });

        //----------------------------------------------------------------------------------------//
        appointmentRecyclerView = view.findViewById(R.id.r_appointment);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        progress = new ProgressDialog(getContext());
        progress.setMessage("Loading...");
        progress.setCancelable(true);
        progress.show();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        degreeRef = mStorageReference.child("User").child(firebaseUser.getUid()).child("/profile.jpg");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);
        childRef = mDatabaseRef.child("Appointment").child(firebaseUser.getUid());
        ref = childRef.orderByChild("date");

        childRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progress.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progress.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Glide
                .with(getActivity())
                .using(new FirebaseImageLoader())
                .load(degreeRef)
                .into(background);

        mDividerItemDecoration = new DividerItemDecoration(
                appointmentRecyclerView.getContext(), LinearLayout.HORIZONTAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(appointmentRecyclerView);
        mAdapter = new AppointmentAdapter(Appointment.class, R.layout.appointment_row,
                AppointmentAdapter.AppointmentViewHolder.class, ref, getContext());


        return view;
    }


    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    public void changeFragment(Fragment f){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.homeframe, f);

        fragmentTransaction.commit();
    }
}
