package com.example.task6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task6.databinding.RecordLayoutBinding;

import java.util.List;

// Adapter for handling the display of employee data in a RecyclerView
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // List to hold the employee data
    private final List<Employee> employeeList;

    // Binding object for the layout of each employee record
    RecordLayoutBinding recordBinding;

    public ListAdapter(List<Employee> employeeList, Context context) {
        super();
        this.employeeList = employeeList;
    }

    // Method to create a new ViewHolder when needed
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        recordBinding = RecordLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(recordBinding);
    }

    // Method to bind data to the ViewHolder at a given position
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(employeeList.get(position));
    }

    // Method to get the total number of items in the adapter
    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    // ViewHolder class to represent each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        RecordLayoutBinding recyclerRowBinding;

        public ViewHolder(RecordLayoutBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }

        // Method to bind data to the views within the ViewHolder
        private void bindView(Employee employee) {
            recyclerRowBinding.txtId.setText(String.valueOf(employee.getId()));
            recyclerRowBinding.txtName.setText(employee.getName());
            recyclerRowBinding.txtDesig.setText(employee.getDesig());
            recyclerRowBinding.txtDept.setText(employee.getDept());
            recyclerRowBinding.txtEmail.setText(employee.getEmailid());
            recyclerRowBinding.txtSalary.setText(String.valueOf(employee.getSalary()));
        }
    }
}
