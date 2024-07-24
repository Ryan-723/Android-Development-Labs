package com.example.task5;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task5.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding detailsBinding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the layout.
        detailsBinding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());
        // Get our data and show it.
        retrievePreferences();
    }

    // This is where we get our data from storage (SharedPreferences)
    private void retrievePreferences() {
        preferences = getSharedPreferences("user_data", MODE_PRIVATE);

        // Get the product name and cost from storage.
        String productName = preferences.getString("name", "");
        float productCost = preferences.getFloat("cost", 0);

        // Show the product details on the screen.
        detailsBinding.productNameText.setText("Product Name: " + productName);
        detailsBinding.productCostText.setText("Cost: $" + productCost);
    }
}