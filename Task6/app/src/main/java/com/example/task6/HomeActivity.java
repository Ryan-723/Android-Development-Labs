package com.example.task6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.task6.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    // View binding for the activity layout
    ActivityHomeBinding homeBinding;

    // Toggle for the navigation drawer
    ActionBarDrawerToggle mToggle;

    // Shared preferences for user login details
    SharedPreferences sharedPref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        init();
    }

    // Initialize the activity
    private void init() {
        // Retrieve user login details from shared preferences
        sharedPref2 = getSharedPreferences("login_details", MODE_PRIVATE);
        homeBinding.txtWelcome.setText("Welcome " + sharedPref2.getString("USER_ID", null) + "!");

        // Set up the navigation drawer toggle
        mToggle = new ActionBarDrawerToggle(this, homeBinding.drawerLayout, homeBinding.materialToolbar, R.string.nav_open, R.string.nav_close);
        homeBinding.drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        setSupportActionBar(homeBinding.materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationDrawer();
        setBottomNavigation();
    }

    // Set up the bottom navigation
    private void setBottomNavigation() {
        homeBinding.bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle bottom navigation item clicks
                if (item.getItemId() == R.id.nav_bottom_search) {

                } else if (item.getItemId() == R.id.nav_bottom_account) {

                } else if (item.getItemId() == R.id.nav_bottom_profile) {

                }
                return false;
            }
        });
    }

    // Set up the navigation drawer
    private void setNavigationDrawer() {
        homeBinding.navView.setNavigationItemSelectedListener(item -> {
            Fragment frag = null;
            // Handle navigation drawer item clicks
            if (item.getItemId() == R.id.nav_add_emp) {
                frag = new AddFragment();
            } else if (item.getItemId() == R.id.nav_delete_emp) {
                frag = new DeleteFragment();
            } else if (item.getItemId() == R.id.nav_list_emp) {
                frag = new ListFragment();
            }
            // Replace the current fragment with the selected one
            if (frag != null) {
                FragmentTransaction frgTrans = getSupportFragmentManager().beginTransaction();
                frgTrans.replace(R.id.frame, frag);
                frgTrans.commit();
                homeBinding.drawerLayout.closeDrawers();
                return true;
            }
            return false;
        });
    }

    // Handle options menu item clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
