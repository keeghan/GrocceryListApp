package com.example.groceryListApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryListApp.DatabaseResources.GroceryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    GroceryAdapter groceryAdapter;
    MainViewModel mainViewModel;
    RecyclerView recyclerView;
    TextView priceSumTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init views
        priceSumTxt = findViewById(R.id.txt_total);
        groceryAdapter = new GroceryAdapter();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        recyclerView = findViewById(R.id.mainRecycler);


        mainViewModel.getGroceryList().observe(this, new Observer<List<GroceryItem>>() {
            @Override
            public void onChanged(List<GroceryItem> groceryItems) {
                groceryAdapter.setGroceryItems(groceryItems);
            }
        });

        //display total of grocery items
        mainViewModel.getPriceSum().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                if (aFloat != null) {
                    String si = aFloat.toString() + " Cedis";
                    priceSumTxt.setText(si);
                }
            }
        });

        recyclerView.setAdapter(groceryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //Implement swiping to delete groceryItem
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainViewModel.delete(groceryAdapter.getGroceryItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //implement recycler item click (update)
        groceryAdapter.setOnClickListener(new GroceryAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                //send grocery Item to AddGroceryActivity to be updated
                GroceryItem groceryItem = groceryAdapter.getGroceryItemAt(position);
                String oldName = groceryItem.getName();
                int oldAmount = groceryItem.getAmount();
                float oldPrice = groceryItem.getPrice();
                Log.e("float value", String.valueOf(oldPrice));
                int id = groceryItem.get_id();

                //pass info of GroceryItem to AddGroceryActivity for update
                Intent intent = new Intent(MainActivity.this, AddGroceryActivity.class);
                intent.putExtra(AddGroceryActivity.EXTRA_TITLE, oldName);
                intent.putExtra(AddGroceryActivity.EXTRA_AMOUNT, oldAmount);
                intent.putExtra(AddGroceryActivity.EXTRA_PRICE, oldPrice);
                intent.putExtra(AddGroceryActivity.EXTRA_ID, id);
                intent.putExtra(AddGroceryActivity.ACTION_ID, 2);
                startActivity(intent);
            }
        });

        //add new note
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddGroceryActivity.class);
                addIntent.putExtra(AddGroceryActivity.ACTION_ID, 1);
                startActivity(addIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clear_Grocery){
            mainViewModel.clearList();
        }
        return super.onOptionsItemSelected(item);
    }
}