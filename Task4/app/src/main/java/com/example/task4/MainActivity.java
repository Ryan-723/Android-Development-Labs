
package com.example.task4;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

// We are importing data binding classes for our layout files
import com.example.task4.databinding.ActivityMainBinding;
import com.example.task4.databinding.Restaurant1Binding;
import com.example.task4.databinding.Restaurant2Binding;
import com.example.task4.databinding.Restaurant3Binding;
import com.example.task4.databinding.Restaurant4Binding;
import com.example.task4.databinding.Restaurant5Binding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // We declare a binding variable for our main activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // We create a binding object for the main activity layout
        setContentView(binding.getRoot());

        // We create binding objects for each restaurant's layout
        Restaurant1Binding restaurant1Binding = Restaurant1Binding.inflate(LayoutInflater.from(this));
        Restaurant2Binding restaurant2Binding = Restaurant2Binding.inflate(LayoutInflater.from(this));
        Restaurant3Binding restaurant3Binding = Restaurant3Binding.inflate(LayoutInflater.from(this));
        Restaurant4Binding restaurant4Binding = Restaurant4Binding.inflate(LayoutInflater.from(this));
        Restaurant5Binding restaurant5Binding = Restaurant5Binding.inflate(LayoutInflater.from(this));

        // We find the LinearLayout with ID restaurantCard in our main layout
        LinearLayout linearLayout = findViewById(R.id.restaurantCard);

        // We add the restaurant layouts to the main layout
        linearLayout.addView(restaurant1Binding.getRoot());
        linearLayout.addView(restaurant2Binding.getRoot());
        linearLayout.addView(restaurant3Binding.getRoot());
        linearLayout.addView(restaurant4Binding.getRoot());
        linearLayout.addView(restaurant5Binding.getRoot());

        // We set up listeners for the RatingBars in each restaurant layout
        setupRatingBarListeners(restaurant1Binding.ratingBar, restaurant1Binding.ratingValueTextView);
        setupRatingBarListeners(restaurant2Binding.ratingBarTwo, restaurant2Binding.ratingValueTextViewTwo);
        setupRatingBarListeners(restaurant3Binding.ratingBarThree, restaurant3Binding.ratingValueTextViewThree);
        setupRatingBarListeners(restaurant4Binding.ratingBarFour, restaurant4Binding.ratingValueTextViewFour);
        setupRatingBarListeners(restaurant5Binding.ratingBarFive, restaurant5Binding.ratingValueTextViewFive);
    }

    // This method sets up a RatingBar listener for a specific RatingBar and TextView
    private void setupRatingBarListeners(android.widget.RatingBar ratingBar, android.widget.TextView textView) {
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            textView.setText(String.valueOf(rating)); // When the RatingBar changes, we update the associated TextView
        });
    }
}
