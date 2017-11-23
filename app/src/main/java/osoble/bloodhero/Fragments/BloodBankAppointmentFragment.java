package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import osoble.bloodhero.Adapters.BloodBankAdapter;
import osoble.bloodhero.Models.BloodBank;
import osoble.bloodhero.R;

public class BloodBankAppointmentFragment extends Fragment {
    private static final String TAG = BloodBankAppointmentFragment.class.getSimpleName();

    private RecyclerView bloodbankRecyclerView;
    private DividerItemDecoration mDividerItemDecoration;
    private BloodBankAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private ProgressDialog progress;
    private SnapHelper snapHelper;

    public BloodBankAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.blood_bank_appointment, container, false);
        Log.i(TAG, "Was Opened");

        getActivity().setTitle("Set Appointment");
        bloodbankRecyclerView = view.findViewById(R.id.r_blood_bank_search);
        bloodbankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        progress = new ProgressDialog(getContext());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

//        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);
        childRef = mDatabaseRef.child("Blood Bank");

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
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                bloodbankRecyclerView.getContext(), LinearLayout.HORIZONTAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(bloodbankRecyclerView);
        mAdapter = new BloodBankAdapter(BloodBank.class, R.layout.blood_bank_row,
                BloodBankAdapter.BloodBankViewHolder.class, childRef, getContext());

        bloodbankRecyclerView.setAdapter(mAdapter);
        bloodbankRecyclerView.setHasFixedSize(true);
        bloodbankRecyclerView.addItemDecoration(mDividerItemDecoration);

        return view;

    }

}
