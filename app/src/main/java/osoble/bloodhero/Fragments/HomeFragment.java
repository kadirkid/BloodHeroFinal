package osoble.bloodhero.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import osoble.bloodhero.R;


public class HomeFragment extends Fragment {
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private HorizontalCalendar horizontalCalendar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position){
                Log.i("----------The Date is ", date.toString());
                Log.i("-------The position is ", Integer.toString(position));
            }
        });

        return view;
    }

    public void changeFragment(Fragment f){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.homeframe, f);

        fragmentTransaction.commit();
    }
}
