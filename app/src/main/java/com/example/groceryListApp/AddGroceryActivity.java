package com.example.groceryListApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.groceryListApp.DatabaseResources.GroceryItem;

import java.util.Locale;

public class AddGroceryActivity extends AppCompatActivity {

    public static final String ACTION_ID =
            "com.example.groceryListApp.AddGroceryActivity.EXTRA_ACTION_ID"; //to check which action called this activity

    //string variable for update intent
    public static final String EXTRA_TITLE =
            "com.example.groceryListApp.AddGroceryActivity.EXTRA_TITLE";
    public static final String EXTRA_AMOUNT =
            "com.example.groceryListApp.AddGroceryActivity.EXTRA_AMOUNT";
    public static final String EXTRA_PRICE =
            "com.example.groceryListApp.AddGroceryActivity.EXTRA_PRICE";
    public static final String EXTRA_ID =
            "com.example.groceryListApp.AddGroceryActivity.EXTRA_ID";


    MainViewModel mainViewModel;
    TextView itemName, itemPrice;
    NumberPicker numberPicker;
    Button addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        addItem = findViewById(R.id.addGrocery);
        itemPrice = findViewById(R.id.groceryPrice);
        itemName = findViewById(R.id.groceryName);
        numberPicker = findViewById(R.id.numberpicker);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(1);

        //check which intent opened Activity
        final Intent intent = this.getIntent();

        if (intent.getExtras().getInt(ACTION_ID) == 2) {
            numberPicker.setValue(intent.getExtras().getInt(EXTRA_AMOUNT));
            itemName.setText(intent.getStringExtra(EXTRA_TITLE));
            itemPrice.setText(String.format(Locale.getDefault(), "%.0f", intent.getFloatExtra(EXTRA_PRICE, 0.0f)));
            addItem.setText(R.string.update_btn);
            setTitle("Update item");
        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float groceryItemPrice;
                if (!itemPrice.getText().toString().isEmpty()) {
                    groceryItemPrice = Float.parseFloat(itemPrice.getText().toString());
                } else {
                    groceryItemPrice = 0.00f;
                }
                int groceryItemNumber = numberPicker.getValue();
                String groceryItemName = itemName.getText().toString();

                if (groceryItemName.trim().isEmpty() || groceryItemNumber == 0) {
                    Toast.makeText(AddGroceryActivity.this, "Enter Item name and amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                GroceryItem tempGroceryItem = new GroceryItem(groceryItemName, groceryItemNumber, groceryItemPrice); //create GroceryItem object for database transaction

                //check if update event triggered activity and update instead of insert
                if (intent.getExtras().getInt(ACTION_ID) == 2) {
                    tempGroceryItem.set_id(intent.getExtras().getInt(EXTRA_ID));
                    mainViewModel.Update(tempGroceryItem);
                    Toast.makeText(AddGroceryActivity.this, "Item Updated", Toast.LENGTH_SHORT).show();
                    return;
                }

                //add grocery item if update is not triggered
                mainViewModel.addGrocery(tempGroceryItem);
                Toast.makeText(AddGroceryActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
            }
        });


    }

}