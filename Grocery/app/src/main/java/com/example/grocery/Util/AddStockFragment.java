package com.example.ndggrocery.Util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ndggrocery.DbHelper;
import com.example.ndggrocery.Util.StockItem;
import com.example.ndggrocery.R;
import com.example.ndggrocery.Util.Dialog;
import com.example.ndggrocery.Util.StockValidation;
import com.example.ndggrocery.databinding.FragmentAddStockBinding;

public class AddStockFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    FragmentAddStockBinding fragmentAddStockBinding;
    boolean isTaxable = true;
    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddStockBinding = FragmentAddStockBinding.inflate(inflater, container, false);
        init();
        return fragmentAddStockBinding.getRoot();
    }

    private void init() {
        dbHelper = new DbHelper(getContext());
        fragmentAddStockBinding.fragmentAddStockSaveBtn.setOnClickListener(this);
        fragmentAddStockBinding.fragmentAddStockCancelBtn.setOnClickListener(this);
        fragmentAddStockBinding.fragmentAddStockTaxableRadioGroup.setOnCheckedChangeListener(this);
        // set taxable as true as default
        fragmentAddStockBinding.fragmentAddStockTaxableYes.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        // save the new stock item info
        if (v.getId() == fragmentAddStockBinding.fragmentAddStockSaveBtn.getId()) {
            EditText itemNameInputController = fragmentAddStockBinding.fragmentAddStockItemName;
            EditText quantityInputController = fragmentAddStockBinding.fragmentAddStockItemQuantity;
            EditText priceInputController = fragmentAddStockBinding.fragmentAddStockItemPrice;
            String itemName = itemNameInputController.getText().toString();
            String quantity = quantityInputController.getText().toString();
            String price = priceInputController.getText().toString();
            // reset errors
            itemNameInputController.setError(null);
            quantityInputController.setError(null);
            priceInputController.setError(null);
            // validation
            StockValidation stockValidation = new StockValidation(itemName, quantity, price);
            boolean isItemNameValid = stockValidation.itemNameValidation();
            if (!isItemNameValid) {
                itemNameInputController.setError(stockValidation.errorMessage);
            }
            boolean isQuantityValid = stockValidation.quantityValidation();
            if (!isQuantityValid) {
                quantityInputController.setError(stockValidation.errorMessage);
            }
            boolean isPriceValid = stockValidation.priceValidation();
            if (!isPriceValid) {
                priceInputController.setError(stockValidation.errorMessage);
            }
            if (isItemNameValid && isQuantityValid && isPriceValid) {
                // add stock item into database
                StockItem stockItem = new StockItem();
                stockItem.itemName = itemName;
                stockItem.quantity = quantity;
                stockItem.price = price;
                stockItem.taxable = isTaxable;
                if (dbHelper.createStockItem(stockItem)) {
                    new Dialog("Create Stock Item Successfully", "The item has been added to database").show(
                            requireActivity().getSupportFragmentManager(), "AddStockSuccessfully"
                    );
                } else {
                    new Dialog("Create Stock Item Failed", "Please try again").show(
                            requireActivity().getSupportFragmentManager(), "AddStockFailed"
                    );
                }
            } else {
                new Dialog("Validate Failed", "Please modify the information of the stock item").show(
                        requireActivity().getSupportFragmentManager(), "AddStockFailed"
                );
            }
            // turn back to the home fragment
        } else if (v.getId() == fragmentAddStockBinding.fragmentAddStockCancelBtn.getId()) {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.home_frame_layout, new MainFragment());
            fragmentTransaction.commit();
        }
    }

    // when the radio group radio items have been clicked
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == fragmentAddStockBinding.fragmentAddStockTaxableYes.getId()) {
            isTaxable = true;
        } else if (checkedId == fragmentAddStockBinding.fragmentAddStockTaxableNo.getId()) {
            isTaxable = false;
        }
    }
}