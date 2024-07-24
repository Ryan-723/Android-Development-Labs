package com.example.task5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.task5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    // Initialize UI components
    private void initialize() {
        binding.submitProduct.setOnClickListener(view -> {
            // Data storage without validation
            preferences = getSharedPreferences("user_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            // Put the cost from the text box into storage.
            editor.putFloat("cost", Float.parseFloat(binding.productCost.getText().toString()));

            // Put the product name from the text box into storage.
            editor.putString("name", binding.productDescription.getText().toString());

            // Save the changes.
            editor.apply();

            // Create an intent to move to the DetailsActivity.
            Intent detailsIntent = new Intent(this, DetailsActivity.class);

            // Start the DetailsActivity.
            startActivity(detailsIntent);
        });
    }
}