package com.example.task6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.task6.databinding.FragmentDeleteBinding;

// Fragment for deleting employee records
public class DeleteFragment extends Fragment implements View.OnClickListener {

    // Binding object for the fragment's layout
    FragmentDeleteBinding fragmentDeleteBinding;

    // Database helper class for managing employee data
    DBHelper dbHelper;

    // Default constructor for the fragment
    public DeleteFragment() {
    }

    // Method called when the fragment is created, responsible for inflating the layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDeleteBinding = FragmentDeleteBinding.inflate(inflater, container, false);

        // Initializing the fragment and its components
        init();

        // Returning the root view of the fragment
        return fragmentDeleteBinding.getRoot();
    }

    // Method to initialize the fragment and set up event listeners
    private void init() {
        // Creating an instance of the DBHelper class for database operations
        dbHelper = new DBHelper(getContext());

        // Setting up the click listener for the delete button
        fragmentDeleteBinding.btnDelete.setOnClickListener(this);
    }

    // Method called when the delete button is clicked
    @Override
    public void onClick(View v) {
        // Checking if the click is from the delete button
        if (v.getId() == fragmentDeleteBinding.btnDelete.getId()) {
            try {
                // Attempting to delete an employee record based on the entered employee ID
                int deletedRow = dbHelper.DeleteEmployee(Integer.parseInt(fragmentDeleteBinding.edtEmpId.getText().toString().trim()));

                // Displaying a toast message based on the deletion result
                if (deletedRow > 0) {
                    Toast.makeText(getContext(), "Employee record deleted successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Employee record does not exist", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                // Displaying a toast message if the entered employee ID is not a number
                Toast.makeText(getContext(), "Employee id should be a number", Toast.LENGTH_LONG).show();
            }
        }
    }
}
