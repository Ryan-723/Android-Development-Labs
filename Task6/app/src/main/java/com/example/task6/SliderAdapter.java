package com.example.task6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Adapter class for the RecyclerView used in the onboarding screen
public class SliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Array to store layout resource IDs for individual screens
    public int[] LayoutScreens;

    // Constructor to initialize the adapter with layout resource IDs
    public SliderAdapter(int[] layoutScreens) {
        LayoutScreens = layoutScreens;
    }

    // Override method to determine the type of view at the given position
    @Override
    public int getItemViewType(int position) {
        return LayoutScreens[position];
    }

    // Override method to create a new ViewHolder for each item
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        // Return a new instance of the custom ViewHolder
        return new SliderViewHolder(view);
    }

    // Override method to bind data to the views
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    // Override method to get the total number of items in the adapter
    @Override
    public int getItemCount() {
        return LayoutScreens.length;
    }

    // Inner class representing the custom ViewHolder for individual items
    public class SliderViewHolder extends RecyclerView.ViewHolder {
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
