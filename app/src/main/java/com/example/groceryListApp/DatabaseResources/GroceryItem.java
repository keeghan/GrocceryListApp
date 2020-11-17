package com.example.groceryListApp.DatabaseResources;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grocery_table")
public class GroceryItem {
    @PrimaryKey(autoGenerate = true)
    private int _id;

    private String name;
    private int amount;
    private float price;


    public GroceryItem( String name, int amount, float price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
