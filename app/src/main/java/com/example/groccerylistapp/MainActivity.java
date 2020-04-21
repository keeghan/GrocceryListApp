package com.example.groccerylistapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //array list for data
    ArrayList<String> glist = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.list_groccery);
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, glist);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_update:
                                onUpdate(position);
                                break;
                            //end of first switch statement
                            case R.id.item_del:
                                Toast.makeText(MainActivity.this,
                                        "Item deleted", Toast.LENGTH_SHORT).show();
                                glist.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                break;
                        }//end of switch statement
                        return true;
                    }
                });
                popupMenu.show();
            }
        }); //end of list.onClickListener
    } //End of on create


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
            _addItem();
        }
        return super.onOptionsItemSelected(item);
    }

    //to add new item to list
    private void _addItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add new Item");
        getLayoutInflater();
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dialog, null, false);
        builder.setView(v);

        final EditText newItem = v.findViewById(R.id.newItem);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!newItem.getText().toString().isEmpty()) {
                    glist.add(newItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    newItem.setError(" Fill Text space");
                }
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /*method to update value in list
     * called in the ListView's OnItemClickListener of the listview*/
    public void onUpdate(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change name");
        getLayoutInflater();
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dialog, null, false);
        builder.setView(v);

        final EditText newItem = v.findViewById(R.id.newItem);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!newItem.getText().toString().isEmpty()) {
                    glist.set(position, newItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,
                            "Item added", Toast.LENGTH_SHORT).show();
                } else {
                    newItem.setError(" Fill Text space");
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    //end of activity
}
