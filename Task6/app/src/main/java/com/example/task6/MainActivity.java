package com.example.task6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    int[] Layouts;
    SliderAdapter sliderAdapter;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view using the generated binding class
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize the layout, adapter, and click listeners
        init();
    }

    private void init() {
        // Array of layout resources for slides
        Layouts = new int[]{
                R.layout.slide_screen_1,
                R.layout.slide_screen_2,
                R.layout.slide_screen_3,
        };

        // Create and set the adapter for the ViewPager
        sliderAdapter = new SliderAdapter(Layouts);
        binding.viewPager.setAdapter(sliderAdapter);

        // Set click listeners for skip and next buttons
        binding.btnSkip.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Handle button clicks
        if (v.getId() == binding.btnNext.getId()) {
            int current = getItem(1);
            if (current < Layouts.length) {
                binding.viewPager.setCurrentItem(current);
                // Change the text on the button for the last slide
                if (current == Layouts.length - 1) {
                    binding.btnNext.setText(R.string.cont);
                }
            } else {
                // Launch the login screen when the last slide is reached
                launchLoginScreen();
            }
        } else if (v.getId() == binding.btnSkip.getId()) {
            // Launch the login screen when the skip button is clicked
            launchLoginScreen();
        }
    }

    private void launchLoginScreen() {
        // Create and start the intent to launch the LoginActivity
        intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }

    private int getItem(int i) {
        // Get the next or previous item index for ViewPager
        return binding.viewPager.getCurrentItem() + i;
    }
}
