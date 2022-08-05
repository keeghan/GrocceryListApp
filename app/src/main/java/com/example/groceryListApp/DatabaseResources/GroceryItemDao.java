package com.example.groceryListApp.DatabaseResources;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroceryItemDao {

    @Insert
    void insert(GroceryItem groceryItem);

    @Update
    void update(GroceryItem groceryItem);

    @Delete
    void delete(GroceryItem groceryItem);


    @Query("SELECT SUM(price*amount) FROM GROCERY_TABLE")
    LiveData<Float> getPriceSum();

    @Query("SELECT * FROM GROCERY_TABLE")
    LiveData<List<GroceryItem>> getGroceryList();

    //Appbar Menu Commands
    @Query("Delete From grocery_table ")
    void clearList();
}
