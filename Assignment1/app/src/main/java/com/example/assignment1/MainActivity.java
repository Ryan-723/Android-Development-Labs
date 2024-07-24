package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import com.example.assignment1.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    // Declarations for selected options and data
    private String selectedBeverageType = "coffee"; // Default choice
    private String selectedBeverageSize = "";
    private String selectedFlavour = "None";
    private Data dataData;
    private ArrayAdapter<String> teaFlavouringsAdapter;
    private ArrayAdapter<String> coffeeFlavouringsAdapter;
    private DatePickerDialog datePicker;
    private boolean isMilkChecked;
    private boolean isSugarChecked;
    private String selectedRegion = "";
    private String selectedStore = "";

    private ActivityMainBinding binding; // Data binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rdCoffee.setChecked(true); // Preselect coffee option
        binding.rdTea.setChecked(false);

        dataData = new Data(this);

        binding.acRegion.setThreshold(2); // Set threshold for region autocomplete
        binding.acRegion.setInputType(InputType.TYPE_NULL);

        setAdapters();
        setListeners();
    }

    // Set adapters for spinners and autocomplete
    private void setAdapters() {
        binding.spnFlavourings.setAdapter(dataData.getCoffeeFlavouringsAdapter());
        binding.acRegion.setAdapter(dataData.getRegionAdapter());
        binding.spnStore.setAdapter(dataData.getEmptyAdapter());
    }

    // Set various listeners for UI elements
    private void setListeners() {
        binding.rgBeverageType.setOnCheckedChangeListener(this);
        binding.rgBeverageSize.setOnCheckedChangeListener(this);
        binding.chkSugar.setOnClickListener(this);
        binding.chkMilk.setOnClickListener(this);

        binding.edtDate.setFocusable(false);
        binding.edtDate.setInputType(InputType.TYPE_NULL);
        binding.edtDate.setOnClickListener(this);

        binding.btnSubmit.setOnClickListener(this);

        binding.spnFlavourings.setOnItemSelectedListener(this);
        binding.spnStore.setOnItemSelectedListener(this);

        binding.acRegion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regionString = binding.acRegion.getText().toString();
                if (!Objects.isNull(dataData.getRegionStores().get(regionString))) {
                    binding.spnStore.setAdapter(dataData.getStoreAdapter(regionString));
                    if (!Objects.isNull(dataData.getRegionStores().get(regionString)) && dataData.getRegionStores().get(regionString).size() > 0) {
                        selectedRegion = regionString;
                        selectedStore = dataData.getRegionStores().get(regionString).get(0);
                    }
                } else {
                    binding.spnStore.setAdapter(dataData.getEmptyAdapter());
                    selectedRegion = "";
                    selectedStore = "";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        if (binding.rgBeverageType.getId() == radioGroup.getId()) {
            if (id == binding.rdTea.getId()) {
                selectedBeverageType = "tea";
                binding.spnFlavourings.setAdapter(teaFlavouringsAdapter);
            } else if (id == binding.rdCoffee.getId()) {
                selectedBeverageType = "coffee";
                binding.spnFlavourings.setAdapter(coffeeFlavouringsAdapter);
            }
        }
        else if (binding.rgBeverageSize.getId() == radioGroup.getId()) {
            if (id == binding.rdSmall.getId()) {
                selectedBeverageSize = "small";
            } else if (id == binding.rdMedium.getId()) {
                selectedBeverageSize = "medium";
            } else {
                selectedBeverageSize = "large";
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.edtDate.getId()) {
            Calendar currentDate = Calendar.getInstance();
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            int month = currentDate.get(Calendar.MONTH);
            int year = currentDate.get(Calendar.YEAR);
            datePicker = new DatePickerDialog(this, (v, selectedYear, selectedMonth, selectedDayOfMonth) ->
                    binding.edtDate.setText(String.format("%d/%d/%d", selectedYear, selectedMonth + 1, selectedDayOfMonth)), year, month, day);
            datePicker.show();
        } else if (view.getId() == binding.chkMilk.getId()) {
            isMilkChecked = binding.chkMilk.isChecked();
        } else if (view.getId() == binding.chkSugar.getId()) {
            isSugarChecked = binding.chkSugar.isChecked();
        } else if (view.getId() == binding.btnSubmit.getId()) {
            validateForm();
        }
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long l) {
        if (!selectedRegion.isEmpty() && parent.getId() == binding.spnStore.getId()) {
            selectedStore = dataData.getRegionStores().get(selectedRegion).get(position);
        } else if (parent.getId() == binding.spnFlavourings.getId()) {
            selectedFlavour = "None";
            if (Objects.equals(selectedBeverageType, "tea")) {
                selectedFlavour = dataData.getCoffeeFlavouringsAdapter().getItem(position);
            } else if (Objects.equals(selectedBeverageType, "coffee")) {
                selectedFlavour = dataData.getTeaFlavouringsAdapter().getItem(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView adapterView) {
    }

    // Function to validate the user input in the form
    private void validateForm() {
        String customerName = binding.edtName.getText().toString();
        String emailAddress = binding.edtEmail.getText().toString();
        String phoneNumber = binding.edtPhNumber.getText().toString();
        String salesDate = binding.edtDate.getText().toString();

        boolean isValid = true;

        if (customerName.isEmpty()) {
            binding.edtName.setError("Customer Name is required");
            isValid = false;
        }

        if (emailAddress.isEmpty()) {
            binding.edtEmail.setError("Email Address is required");
            isValid = false;
        } else if (!isValidEmail(emailAddress)) {
            binding.edtEmail.setError("Invalid Email Address Format");
            isValid = false;
        }

        if (phoneNumber.isEmpty()) {
            binding.edtPhNumber.setError("Phone Number is required");
            isValid = false;
        } else if (!isValidPhoneNumber(phoneNumber)) {
            binding.edtPhNumber.setError("Invalid Phone Number Format");
            isValid = false;
        }

        if (salesDate.isEmpty()) {
            binding.edtDate.setError("Sales Date is required");
            isValid = false;
        }

        if (!selectedBeverageType.equals("tea") && !selectedBeverageType.equals("coffee")) {
            displaySnackbar("Beverage type should be selected");
            isValid = false;
        } else if (selectedBeverageSize.isEmpty()) {
            displaySnackbar("Beverage Size should be selected");
            isValid = false;
        } else if (selectedRegion.isEmpty() || selectedStore.isEmpty()) {
            displaySnackbar("Please select a Region and Store");
            isValid = false;
        } else if (!isValidSalesDate(salesDate)) {
            displaySnackbar("Sales Date should be equal to or before the current date");
            binding.edtDate.setError("Invalid Sales Date");
            isValid = false;
        }

        if (isValid) {
            Bill bill = new Bill(customerName, emailAddress, phoneNumber, selectedBeverageSize, selectedBeverageType, isMilkChecked, isSugarChecked, selectedFlavour, selectedRegion, selectedStore, salesDate);
            displayAlertDialog(bill.output());
        } else {
            displayAlertDialog("Please correct the form errors.");
        }
    }

    // Function to validate email using regex
    private boolean isValidEmail(String email) {
        Pattern emailRegex = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        return emailRegex.matcher(email).matches();
    }

    // Function to validate phone number format
    private boolean isValidPhoneNumber(String phoneNumber) {
        Pattern phoneRegex = Pattern.compile("^[0-9]{10}$");
        return phoneRegex.matcher(phoneNumber).matches();
    }

    // Function to validate sales date
    private boolean isValidSalesDate(String salesDate) {
        String[] dateArray = salesDate.split("/");

        if (dateArray.length != 3) {
            return false;
        }

        int year, month, day;

        if (!isInteger(dateArray[0]) || !isInteger(dateArray[1]) || !isInteger(dateArray[2])) {
            return false;
        }

        year = Integer.parseInt(dateArray[0]);
        month = Integer.parseInt(dateArray[1]);
        day = Integer.parseInt(dateArray[2]);

        LocalDate currentDate = LocalDate.now();

        if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }

        LocalDate selectedDate = LocalDate.of(year, month, day);

        return !selectedDate.isAfter(currentDate);
    }

    // Function to check if a string is an integer
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Function to display a snackbar
    private void displaySnackbar(String message) {
        Snackbar snackbar = Snackbar.make(binding.ltHost, message, BaseTransientBottomBar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", v -> {});

        View snackbarView = snackbar.getView();
        ViewGroup.LayoutParams params = snackbarView.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) params;
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.setMargins(0, 0, 0, 100);
            snackbarView.setLayoutParams(layoutParams);
        }

        snackbar.show();
    }

    // Function to display an alert dialog
    private void displayAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Summary");
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }
}

// Data class for storing region, store, and adapters
class Data {

    private final Context context;
    private final HashMap<String, ArrayList<String>> regionStores = new HashMap<>();
    private ArrayAdapter<String> coffeeFlavouringsAdapter;
    private ArrayAdapter<String> teaFlavouringsAdapter;
    private ArrayAdapter<String> emptyAdapter;

    public HashMap<String, ArrayList<String>> getRegionStores() {
        return regionStores;
    }

    public ArrayAdapter<String> getCoffeeFlavouringsAdapter() {
        return coffeeFlavouringsAdapter;
    }

    public ArrayAdapter<String> getTeaFlavouringsAdapter() {
        return teaFlavouringsAdapter;
    }

    public ArrayAdapter<String> getEmptyAdapter() {
        return emptyAdapter;
    }

    public Data(MainActivity context) {
        addStores("Waterloo", new String[]{"65 University Ave E", "415 King St", "585 Weber St"});
        addStores("London", new String[]{"616 Wharncliffe Rd", "1885 Huron St", "670 Wonderland Road", "1181 Highbury Ave"});
        addStores("Milton", new String[]{"900 Steeles Ave", "80 Market Dr", "820 Main St"});
        addStores("Mississauga", new String[]{"144 Dundas St", "30 Eglinton Ave", "6075 Creditview Rd"});

        this.context = context;
        emptyAdapter = outputAdapter(new String[]{"None"});
        coffeeFlavouringsAdapter = outputAdapter(new String[]{"None", "Pumpkin Spice", "Chocolate"});
        teaFlavouringsAdapter = outputAdapter(new String[]{"None", "Lemon", "Ginger"});
    }

    private void addStores(String region, String[] stores) {
        regionStores.put(region, new ArrayList<>(Arrays.asList(stores)));
    }

    public ArrayAdapter<String> getRegionAdapter() {
        String[] cities = new String[regionStores.size()];
        regionStores.keySet().toArray(cities);
        return outputAdapter(cities);
    }

    public ArrayAdapter<String> getStoreAdapter(String region) {
        String[] stores = new String[regionStores.get(region).size()];
        regionStores.get(region).toArray(stores);
        return outputAdapter(stores);
    }

    private ArrayAdapter<String> outputAdapter(String[] Data) {
        return new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Data);
    }
}

// Class to store the order details
class Bill {
    private final String customerName;
    private final String emailAddress;
    private final String phoneNumber;
    private final String beverageType;
    private final boolean addMilk;
    private final boolean addSugar;
    private final String flavourings;
    private final String beverageSize;
    private final String region;
    private final String store;
    private final String salesDate;

    // Constructor to initialize the Bill object
    public Bill(String customerName, String emailAddress, String phoneNumber, String beverageSize, String beverageType, boolean addMilk, boolean addSugar, String flavourings, String region, String store, String salesDate) {
        this.customerName = customerName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.beverageType = beverageType;
        this.beverageSize = beverageSize;
        this.addMilk = addMilk;
        this.addSugar = addSugar;
        this.flavourings = flavourings;
        this.region = region;
        this.store = store;
        this.salesDate = salesDate;
    }

    // Function to calculate the total bill
    public String calculateTotalBill() {
        double beverageSizePrice = 0.0;
        double additionalPrice = 0.0;
        double flavoringsPrice = 0.0;

        switch (beverageType) {
            case "tea":
                switch (beverageSize) {
                    case "small":
                        beverageSizePrice = 1.5;
                        break;
                    case "medium":
                        beverageSizePrice = 2.5;
                        break;
                    case "large":
                        beverageSizePrice = 3.25;
                        break;
                }
                switch (flavourings) {
                    case "Lemon":
                        flavoringsPrice = 0.25;
                        break;
                    case "Ginger":
                        flavoringsPrice = 0.75;
                        break;
                }
                break;
            case "coffee":
                switch (beverageSize) {
                    case "small":
                        beverageSizePrice = 1.75;
                        break;
                    case "medium":
                        beverageSizePrice = 2.75;
                        break;
                    case "large":
                        beverageSizePrice = 3.75;
                        break;
                }
                switch (flavourings) {
                    case "Pumpkin Spice":
                        flavoringsPrice = 0.5;
                        break;
                    case "Chocolate":
                        flavoringsPrice = 0.75;
                        break;
                }
                break;
        }

        if (addMilk) {
            additionalPrice = 1.25;
        }
        if (addSugar) {
            additionalPrice += 1;
        }

        double totalCost = beverageSizePrice + flavoringsPrice + additionalPrice;
        return String.format("%.2f", totalCost * 1.13);
    }

    // Function to format and display the order summary
    public String output() {
        String output = "Customer Name: " + customerName + "\n" +
                "Email Address: " + emailAddress + "\n" +
                "Phone Number: " + phoneNumber + "\n" +
                "Type of Beverage: " + beverageType + "\n" +
                "Beverage Size: " + beverageSize + "\n" +
                "Flavourings: " + flavourings + "\n" +
                "Region: " + region + "\n" +
                "Store: " + store + "\n" +
                "Total Bill Amount: $" + calculateTotalBill() + "\n";

        return output;
    }
}
