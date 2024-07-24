package com.example.task6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task6.databinding.ActivityTestHomeBinding;

public class TestHomeActivity extends AppCompatActivity {

    // Declare variables for layout binding and SharedPreferences
    ActivityTestHomeBinding testHomeBinding;
    SharedPreferences sharedPref1;

    // Method called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testHomeBinding = ActivityTestHomeBinding.inflate(getLayoutInflater());
        setContentView(testHomeBinding.getRoot());
        // Call the initialization method
        init();
    }

    // Custom method for initialization
    private void init() {
        // Initialize SharedPreferences with a specific file and mode
        sharedPref1 = getSharedPreferences("login_details", Context.MODE_PRIVATE);
        // Retrieve values from SharedPreferences
        String USER_ID = sharedPref1.getString("USER_ID", null);
        String EMAIL_ID = sharedPref1.getString("EMAIL_ID", null);
        // Set the welcome message in the UI
        testHomeBinding.txtWelcome.setText("Welcome " + USER_ID + " !\n" + EMAIL_ID);
    }
}
