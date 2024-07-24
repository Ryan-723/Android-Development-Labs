package com.example.ndgmealdelivery;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.Context;
import com.example.ndgmealdelivery.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Pattern;

// Definition of the MainActivity class, which extends AppCompatActivity and implements other listeners.
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener{


    // Declaration of variables and objects used in this class.
    ActivityMainBinding binding;
    MembershipCost mCost;
    DatePickerDialog datePicker;
    String plan = "";
    String planType = "";
    String[] monthlyOptions = {"Cooked Meal", "Uncooked Meal"};
    String[] yearlyPlanArray = {"Veggies with Recipe", "Veggies Only"};

    String[] deliveryOptions = {"Store pick-up", "Community Box", "Door Steps"};
    ArrayAdapter monthlyOptionTypes;
    ArrayAdapter yearlyOptions;
    String deliveryTypes = deliveryOptions[0];
    ArrayAdapter deliveryTypesAdapter;
    boolean lemonadeOption;
    boolean milkOption;

    // Override the onCreate method to set up the initial state of the activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        // Create an instance of the MembershipCost class
        mCost = new MembershipCost(this);

        SetListeners();
        SetAdaptors();
    }

    // Set adapters for spinner views using the MembershipCost class.
    private void SetAdaptors() {
        monthlyOptionTypes = mCost.GenerateAdapter(monthlyOptions);
        yearlyOptions = mCost.GenerateAdapter(yearlyPlanArray);
        deliveryTypesAdapter = mCost.GenerateAdapter(deliveryOptions);
        binding.spnDesignTypes.setAdapter(deliveryTypesAdapter);
    }

    // Display a toast message with the given string.
    public void testVar(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    // This method sets the listeners for the various views in the layout
    private void SetListeners() {
        binding.edtDate.setFocusable(false);
        binding.edtDate.setInputType(InputType.TYPE_NULL);
        binding.edtDate.setOnClickListener(this);
        binding.chkDesigns.setOnClickListener(this);
        binding.chkUpdates.setOnClickListener(this);
        // Set the item selected listeners for the spinners to get their selected values
        binding.spnDesignTypes.setOnItemSelectedListener(this);
        binding.rgSubsPlan.setOnCheckedChangeListener(this);
        binding.spnPlanType.setOnItemSelectedListener(this);
        // Set the click listener for the submit button to validate the input field
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.edtDate.getId()) {
            Calendar cal = Calendar.getInstance();

            int saleDay = cal.get(Calendar.DAY_OF_MONTH);
            int saleMonth = cal.get(Calendar.MONTH);
            int saleYear = cal.get(Calendar.YEAR);

            // Create a date picker dialog with the current date as the default value
            datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> binding.edtDate.setText(String.format("%d/%d/%d", year, month + 1, dayOfMonth)), saleYear, saleMonth, saleDay);

            // set the maximum date to the current date
            datePicker.getDatePicker().setMaxDate(cal.getTimeInMillis());

            // Show the date picker dialog
            datePicker.show();
        }

        else if (v.getId() == binding.chkDesigns.getId()) {
            lemonadeOption = binding.chkDesigns.isChecked();
        }
        else if (v.getId() == binding.chkUpdates.getId()) {
            milkOption = binding.chkUpdates.isChecked();
        }
        else if (v.getId() == binding.btnSubmit.getId()) {
            inputValidation(binding, plan, planType, deliveryTypes, lemonadeOption, milkOption);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == binding.spnPlanType.getId()) {
            if (Objects.equals(plan, "Monthly")) {
                planType = monthlyOptions[position];
            } else if (Objects.equals(plan, "Yearly")) {
                planType = yearlyPlanArray[position];
            }
        }
        else if (parent.getId() == binding.spnDesignTypes.getId()) {
            deliveryTypes = deliveryOptions[position];
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (group.getId() == binding.rgSubsPlan.getId()) {
            if (checkedId == binding.rdMonthly.getId()) {
                plan = "Monthly";
                binding.spnPlanType.setAdapter(monthlyOptionTypes);
            } else if (checkedId == binding.rdYearly.getId()) {
                plan = "Yearly";
                binding.spnPlanType.setAdapter(yearlyOptions);
            } else {
                plan = "";
                binding.spnPlanType.setAdapter(null);
            }
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // This method checks if the input values from the user are valid and returns a boolean value, It also displays a message to the user based on the validation result
    public static boolean inputValidation(ActivityMainBinding binding, String plan, String planType, String deliveryTypes, boolean lemonadeOption, boolean milkOption) {
        String customer_name = "";
        String phone = "";
        String membership_date = "";

        // Check if the customer name is not empty
        if (!isCustomerNameValid(binding.edtName.getText().toString(), binding)) {
            return false;
        }

        // Check if the membership plan is either Yearly or Monthly
        if (!isValidMembershipPlan(plan, binding)) {
            return false;
        }

        // Check if the plan type is not empty
        if (!isPlanTypeValid(planType, binding)) {
            return false;
        }

        // Check if the delivery types are not empty
        if (!isDeliveryTypesValid(deliveryTypes, binding)) {
            return false;
        }

        // Check if the phone number is valid (10 digits)
        if (!isPhoneNumberValid(binding.edtPhone.getText().toString(), binding)) {
            return false;
        }

        // Check if the membership date is not empt
        if (!isMembershipDateValid(binding.edtDate.getText().toString(), binding)) {
            return false;
        }

        // If all checks pass, return true and output

        SubOutput subOutput = new SubOutput(customer_name ,phone , plan, planType, lemonadeOption, milkOption, deliveryTypes, membership_date);
        messageOutput(binding, subOutput.messageConcat());
        return true;
    }

    // This method checks if the customer name is not empty and returns a boolean value
    private static boolean isCustomerNameValid(String name, ActivityMainBinding binding) {
        if (name.isEmpty()) {
            showErrorAndReturn(binding, "Customer Name is Empty", binding.edtName);
            return false;
        }
        return true;
    }

    // This method checks if the membership plan is either Yearly or Monthly and returns a boolean value
    private static boolean isValidMembershipPlan(String plan, ActivityMainBinding binding) {
        if (!plan.equals("Yearly") && !plan.equals("Monthly")) {
            showErrorAndReturn(binding, "Select a membership Plan");
            return false;
        }
        return true;
    }

    // This method checks if the plan type is not empty and returns a boolean value
    private static boolean isPlanTypeValid(String planType, ActivityMainBinding binding) {
        if (planType.isEmpty()) {
            showErrorAndReturn(binding, "Select a Plan Type");
            return false;
        }
        return true;
    }

    // This method checks if the delivery types are not empty and returns a boolean value
    private static boolean isDeliveryTypesValid(String deliveryTypes, ActivityMainBinding binding) {
        if (deliveryTypes.isEmpty()) {
            showErrorAndReturn(binding, "Select an Additionals");
            return false;
        }
        return true;
    }

    // This method checks if the phone number is valid (10 digits) and returns a boolean value
    private static boolean isPhoneNumberValid(String phone, ActivityMainBinding binding) {
        Pattern phone_regex = Pattern.compile("\\d{10}");
        if (!phone_regex.matcher(phone).matches()) {
            showErrorAndReturn(binding, "Phone number not valid", binding.edtPhone, "Phone number is 10 digit numeral");
            return false;
        }
        return true;
    }

    // This method checks if the membership date is not empty and returns a boolean valu
    private static boolean isMembershipDateValid(String date, ActivityMainBinding binding) {
        if (date.isEmpty()) {
            showErrorAndReturn(binding, "Membership Date Should not be Empty", binding.edtDate, "Empty Membership Date not allowed");
            return false;
        }
        return true;
    }

    // This method displays an error message to the user using a toast
    private static void showErrorAndReturn(ActivityMainBinding binding, String errorMessage) {
        messageOutput(binding, errorMessage);
    }

    // This method displays an error message to the user using a toast and sets an error on the edit text view
    private static void showErrorAndReturn(ActivityMainBinding binding, String errorMessage, EditText editText) {
        messageOutput(binding, errorMessage);
        editText.setError(errorMessage);
    }

    // This method displays an error message to the user using a toast and sets an error on the edit text view with a different text
    private static void showErrorAndReturn(ActivityMainBinding binding, String errorMessage, EditText editText, String errorText) {
        messageOutput(binding, errorMessage);
        editText.setError(errorText);
    }

    

    public static void messageOutput(ActivityMainBinding binding, String message)
    {
        Snackbar snackbar = Snackbar.make(binding.mainActivityLayout, message, BaseTransientBottomBar.LENGTH_INDEFINITE);
        View snackBarView = snackbar.getView();
        TextView textViewOutput = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textViewOutput.setMaxLines(99);
        snackbar.setAction("OK", v -> {
        }).show();
    }
}
class MembershipCost {

    private final Context context;

    public MembershipCost(Context context) {
        this.context = context;
    }
    public ArrayAdapter GenerateAdapter(String[] source) {
        return new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, source);
    }

}

class SubOutput {
    private final String customer_name;
    private final String phone;
    private final String plan;
    private final String planType;
    private final boolean lemonadeOption;
    private final boolean milkOption;
    private final String deliveryTypes;
    private final String membership_date;


    public SubOutput(String customer_name, String phone, String plan, String planType, boolean lemonadeOption, boolean milkOption, String deliveryTypes, String membership_date)
    {
        this.customer_name = customer_name;
        this.phone = phone;
        this.plan = plan;
        this.planType = planType;
        this.deliveryTypes = deliveryTypes;
        this.membership_date = membership_date;
        this.lemonadeOption = lemonadeOption;
        this.milkOption = milkOption;
    }
    public String messageConcat()
    {
        String print_string = "";
        print_string += "Customer Name:" + customer_name + "\n";
        print_string += "Phone:" + phone + "\n";
        print_string += "Membership Plan:" + plan + "\n";
        print_string += "Plan Type:" + planType + "\n";
        print_string += "Type of Delivery:" + deliveryTypes + "\n";
        print_string += "Membership Date" + membership_date + "\n";
        print_string += "Milk Additional: " + (milkOption ? "Yes" : "No") + "\n";
        print_string += "Lemonade Additional: " + (lemonadeOption ? "Yes" : "No") + "\n";
        return print_string;
    }
}

