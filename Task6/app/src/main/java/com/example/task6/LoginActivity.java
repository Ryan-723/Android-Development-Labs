package com.example.task6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task6.databinding.ActivityLoginBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding loginBinding;
    Intent intent2;
    SharedPreferences sharedPref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        // Initialize the login functionality
        init();
    }

    private void init() {
        // Set a click listener for the login button
        loginBinding.btnLogin.setOnClickListener(this);
        // Initialize SharedPreferences for storing login details
        sharedPref1 = getSharedPreferences("login_details", Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        // Check if the login button is clicked
        if (v.getId() == loginBinding.btnLogin.getId()) {
            // Retrieve input values
            String username = loginBinding.edtUserId.getText().toString().trim();
            String password = loginBinding.edtPasswd.getText().toString().trim();
            String email = loginBinding.edtEmailid.getText().toString().trim();
            // Check if the entered credentials are valid
            if (username.equals("NUser") && password.equals("NPassword")) {
                // Save user details in SharedPreferences
                SharedPreferences.Editor editor = sharedPref1.edit();
                editor.putString("USER_ID", username);
                editor.putString("EMAIL_ID", email);
                editor.commit();
                // Navigate to the home activity
                intent2 = new Intent(this, HomeActivity.class);
                startActivity(intent2);
            } else {
                // Show a material dialog for login failure
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Login Failed")
                        .setMessage("Invalid User ID and/or Password")
                        .setNegativeButton("Cancel", (dialog, which) -> {
                        })
                        .setPositiveButton("Ok", (dialog, which) -> {
                        })
                        .show();
            }
        }
    }
}
