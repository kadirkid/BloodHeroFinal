package osoble.bloodhero.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.UUID;

import osoble.bloodhero.Fragments.BloodBankAppointmentFragment;
import osoble.bloodhero.Fragments.HomeFragment;
import osoble.bloodhero.Fragments.LearnFragment;
import osoble.bloodhero.Fragments.ProfileFragment;
import osoble.bloodhero.Fragments.SearchFragment;
import osoble.bloodhero.Models.BloodBank;
import osoble.bloodhero.R;

public class HomeActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView nav;

    //private DatabaseReference databaseReference;
    //private DatabaseReference childref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        /*
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("Blood Bank");
        createCentersList();
         */



        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        nav = (NavigationView) findViewById(R.id.navigationView);
        fragmentManager = getSupportFragmentManager();

        nav.getMenu().findItem(R.id.nav_home).setChecked(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        changeFragment(new HomeFragment());
                        break;

                    case R.id.nav_profile:
                        changeFragment(new ProfileFragment());
                        break;

                    case R.id.nav_donor:
                        changeFragment(new SearchFragment());
                        break;

                    case R.id.nav_appointment:
                        changeFragment(new BloodBankAppointmentFragment());
                        break;

                    case R.id.nav_Learn:
                        changeFragment(new LearnFragment());
                        break;

                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                }

                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changeFragment(new HomeFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    /*
    Was already called to populate the database.
    It is no longer needed
     */
    private void createCentersList() {
        // create centers list
        ArrayList<BloodBank> mBloodCenters = new ArrayList<>();

        mBloodCenters.add(new BloodBank(
                "Sharjah Blood Transfuion and Research Center"
                , "06-5582111"
                , "06-5388717"
                , "University City Street - Region Moweleh\n" +
                "P.O Box: 2605 Sharjah"
                , UUID.randomUUID().toString()
        ));

        mBloodCenters.add(new BloodBank(
                "Abu Dhabi Blood Bank Center"
                , "02-6656508"
                , "02-6661464"
                , "Manhal Street - Abu Dhabi"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Latifa (Al Wasl) Hospital"
                , "04-2193221"
                , "04-2193548"
                , "Oud Metha Road - Dubai"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank – Tawam Hospital"
                , "03-7623088"
                , "03-7632557"
                , "Jimi - Al Ain"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Zayed Miltary Hospital"
                , "02-4055243"
                , "02-4492075"
                , "Musaffah Road - Abu Dhabi"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Al Baraha Hospital"
                , "04-2731451"
                , "04-2731757"
                , "Arabic Gulf Street - Dubai"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Al Dhaid Hospital"
                , "06-8020245"
                , "06-8822595"
                , "Hisham Bin Abdul Rahman Street – Al Dhaid"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Kalba Hospital"
                , "09-2030256"
                , "09-2030249"
                , "Ali Bin Abi Talib Street –\n" +
                "Al Baraha area - Kalba"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Khorfakkan Hospital"
                , "09-2080316"
                , "09-2382515"
                , "Khaled Bin Al Waleed Street - Yarmouk district\n" +
                "P.O Box: 10 449\n" +
                "Khor Fakkan"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Ajman Khalifa Hospital"
                , "06-7050539"
                , "06-7433372"
                , "Next to the Ajman University of Science and Technology - Ajman"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Umm Al Quwain Hospital"
                , "06-7657220"
                , "06-7650370"
                , "Mualla Street - opposite the National Bank of Dubai\n" +
                "Umm Al Quwain"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Saqar Hospital"
                , "07-2023167"
                , "07-2225001"
                , "Algosaidat Area –\n" +
                "Ras Al Khaimah"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Fujairah Hospital"
                , "09-2020503"
                , "09-2020516"
                , "Fujairah Hospital - Nagmyat St.- Front Al-Futtaim Motors - Fujairah"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Dibba Al Fujairah Hospital"
                , "09-2061292"
                , "09-2440422"
                , "Sheikh Zayed Road - Dibba Fujairah"
                , UUID.randomUUID().toString()
        ));
        mBloodCenters.add(new BloodBank(
                "Blood Bank - Al Ain Hospital"
                , "03-7072475"
                , "03-7626721"
                , "Al- Ain City"
                , UUID.randomUUID().toString()
        ));

        for(final BloodBank bloodBank: mBloodCenters){
            /*
            childref.child(bloodBank.getID()).setValue(bloodBank)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                            }
                            else{
                                Toast.makeText(getApplicationContext(),
                                        "Failed to add BloodBank: " + bloodBank.getID()
                                                + "to the database",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
             */
        }
    }

    public void changeFragment(Fragment f){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.homeframe, f);

        fragmentTransaction.commit();
    }
}
