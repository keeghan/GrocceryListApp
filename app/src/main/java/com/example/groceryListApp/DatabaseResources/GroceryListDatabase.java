package com.example.groceryListApp.DatabaseResources;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {GroceryItem.class},version = 1,exportSchema = false)
public abstract class GroceryListDatabase extends RoomDatabase{
    private static GroceryListDatabase instance;

   public abstract GroceryItemDao groceryItemDao();

    public static synchronized GroceryListDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GroceryListDatabase.class,
                    "grocery_database").fallbackToDestructiveMigration()
                  //  .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //only for pre-filling database
   /* private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };*/
}
