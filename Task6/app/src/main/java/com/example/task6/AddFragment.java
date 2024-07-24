package com.example.task6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.task6.databinding.FragmentAddBinding;

public class AddFragment extends Fragment implements View.OnClickListener {

    // Binding object for the fragment's layout
    FragmentAddBinding fragmentAddBinding;

    // Database helper class for managing employee data
    DBHelper dbHelper;

    // Boolean variable to track the status of employee record insertion
    Boolean insertStatus;

    // Default constructor for the fragment
    public AddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddBinding = FragmentAddBinding.inflate(inflater, container, false);

        // Initializing the fragment and its components
        init();

        // Returning the root view of the fragment
        return fragmentAddBinding.getRoot();
    }

    // Method to initialize the fragment and set up event listeners
    private void init() {
        // Setting up the click listener for the submit button
        fragmentAddBinding.btnSubmit.setOnClickListener(this);

        // Creating an instance of the DBHelper class for database operations
        dbHelper = new DBHelper(getContext());
    }

    // Method to create an Employee object with data from input fields
    private Employee createEmploy() {
        // Creating an Employee object
        Employee empObj = new Employee();

        // Setting the employee details from the input fields
        empObj.setName(fragmentAddBinding.edtName.getText().toString().trim());
        empObj.setDesig(fragmentAddBinding.edtDesig.getText().toString().trim());
        empObj.setDept(fragmentAddBinding.edtDept.getText().toString().trim());
        empObj.setEmailid(fragmentAddBinding.edtEmail.getText().toString().trim());
        empObj.setSalary(Integer.parseInt(fragmentAddBinding.edtSalary.getText().toString().trim()));

        // Returning the created Employee object
        return empObj;
    }

    // Method called when the submit button is clicked
    @Override
    public void onClick(View v) {
        // Checking if the click is from the submit button
        if (v.getId() == fragmentAddBinding.btnSubmit.getId()) {
            // Creating an Employee object with data from input fields
            Employee empObj = createEmploy();

            // Inserting the employee record into the database and updating the insertStatus variable
            insertStatus = dbHelper.insertEmployee(empObj);

            // Displaying a toast message based on the insertion status
            if (insertStatus) {
                Toast.makeText(getContext(), "Employee record is added successfully.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Record inserted failed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
