package com.example.task6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LoginFailDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create an AlertDialog.Builder object
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

        // Set the title for the dialog
        builder1.setTitle("Login Failed");

        // Set the message for the dialog
        builder1.setMessage("Invalid User ID and/or Password");

        // Set a negative button with a click listener
        builder1.setNegativeButton("Cancel", (dialog, which) -> {
            // Display a toast when the negative button is clicked
            Toast.makeText(getContext(), "Negative Button Clicked", Toast.LENGTH_LONG).show();
        });

        // Create and return the AlertDialog
        return builder1.create();
    }
}
