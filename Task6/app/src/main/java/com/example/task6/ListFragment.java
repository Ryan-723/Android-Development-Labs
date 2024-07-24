package com.example.task6;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task6.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;

// Fragment to display a list of employees
public class ListFragment extends Fragment {

    // View binding for the fragment layout
    FragmentListBinding fragmentListBinding;

    // List to store employee data
    List<Employee> emList = new ArrayList<>();

    // Database helper for CRUD operations
    DBHelper dbHelper;

    // Adapter for the RecyclerView
    ListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentListBinding = FragmentListBinding.inflate(inflater, container, false);
        init();
        return fragmentListBinding.getRoot();
    }

    // Initialize the fragment
    private void init() {
        // Create a new instance of the database helper
        dbHelper = new DBHelper(getContext());

        // Read employee data from the database
        Cursor cursor1 = dbHelper.readEmployees();

        // Check if any records are found
        if (cursor1.getCount() == 0) {
            Toast.makeText(getContext(), "No employee records found", Toast.LENGTH_SHORT).show();
        } else {
            // Loop through the cursor and populate the employee list
            cursor1.moveToFirst();
            do {
                Employee empObj = new Employee();
                empObj.setId(cursor1.getInt(0));
                empObj.setName(cursor1.getString(1));
                empObj.setDesig(cursor1.getString(2));
                empObj.setDept(cursor1.getString(3));
                empObj.setEmailid(cursor1.getString(4));
                empObj.setSalary(cursor1.getInt(5));
                emList.add(empObj);
            } while (cursor1.moveToNext());

            // Close the cursor and database
            cursor1.close();
            dbHelper.close();

            // Bind the adapter to the RecyclerView
            bindAdapter();
        }
    }

    // Bind the adapter to the RecyclerView
    private void bindAdapter() {
        // Set up the RecyclerView layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentListBinding.rcView.setLayoutManager(layoutManager);

        // Create a new adapter and set it to the RecyclerView
        listAdapter = new ListAdapter(emList, getContext());
        fragmentListBinding.rcView.setAdapter(listAdapter);

        // Notify the adapter that the data set has changed
        listAdapter.notifyDataSetChanged();
    }
}
