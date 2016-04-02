package com.ioptime.tactuck;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static TextView titleTV;
    ImageView menuIV;
    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //setting profile fragment
        ProfileFragment fragment = new ProfileFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // menuIV = (ImageView) toolbar.findViewById(R.id.menuIV);
        titleTV = (TextView) toolbar.findViewById(R.id.titleTV);
        titleTV.setText("Profile");
//        menuIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                    drawerLayout.closeDrawer(Gravity.LEFT); //CLOSE Nav Drawer!
//                } else {
//                    drawerLayout.openDrawer(Gravity.LEFT); //OPEN Nav Drawer!
//                }
//            }
//        });
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(false); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // dis
        //toolbar.setLogo(R.drawable.logo);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigationView.inflateHeaderView(R.layout.header);
        ImageView iv = (ImageView) headerView.findViewById(R.id.profile_image);
        iv.setImageResource(R.drawable.circle);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.profile:
                        ProfileFragment fragment = new ProfileFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        //  toolbar.setTitle("Profile" + "");
                        titleTV.setText("Profile");
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.edit_profile:
                        EditProfileFragment editProfile = new EditProfileFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionEP = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        fragmentTransactionEP.replace(R.id.frame, editProfile);
                        fragmentTransactionEP.commit();
                        //  toolbar.setTitle("Edit Profile" + "");
                        titleTV.setText("Edit Profile");
                        return true;
                    case R.id.see_ride:
                        FindTackTukTabsFragment findtack = new FindTackTukTabsFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionFT = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        fragmentTransactionFT.replace(R.id.frame, findtack);
                        fragmentTransactionFT.commit();
                        titleTV.setText("Find your tactuk");
                        return true;
                    case R.id.share_ride:
                        ShareTucFragment shareRide = new ShareTucFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionSR = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        fragmentTransactionSR.replace(R.id.frame, shareRide);
                        fragmentTransactionSR.commit();
                        //  toolbar.setTitle("Share ride" + "");
                        titleTV.setText("Share tactuk");
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }


}
