package com.example.groceryListApp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.groceryListApp.DatabaseResources.GroceryItem;
import com.example.groceryListApp.DatabaseResources.GroceryItemDao;
import com.example.groceryListApp.DatabaseResources.GroceryListDatabase;

import java.util.List;


public class MainViewModel extends AndroidViewModel {
    private final LiveData<List<GroceryItem>> groceryItems;
    private final LiveData<Float> priceSum;
    private final GroceryItemDao groceryItemDao;


    public MainViewModel(@NonNull Application application) {
        super(application);
        GroceryListDatabase database = GroceryListDatabase.getInstance(application);
        groceryItemDao = database.groceryItemDao();
        groceryItems = groceryItemDao.getGroceryList();
        priceSum = groceryItemDao.getPriceSum();
    }

    public LiveData<List<GroceryItem>> getGroceryList() {
        return groceryItems;
    }

    public LiveData<Float> getPriceSum() {
        return priceSum;
    }

    public void clearList() {
        new clearListAsyncTask(groceryItemDao).execute();
    }

    public void addGrocery(GroceryItem groceryItem) {
        new AddGroceryAsyncTask(groceryItemDao).execute(groceryItem);
    }

    public void Update(GroceryItem groceryItem) {
        new UpdateGroceryItemAsyncTask(groceryItemDao).execute(groceryItem);
    }

    public void delete(GroceryItem groceryItem) {
        new DeleteGroceryAsyncTask(groceryItemDao).execute(groceryItem);
    }

    //AsyncTask to perform database transaction on background threads
    private static class DeleteGroceryAsyncTask extends AsyncTask<GroceryItem, Void, Void> {
        private final GroceryItemDao groceryItemDao;

        public DeleteGroceryAsyncTask(GroceryItemDao groceryItemDao) {
            this.groceryItemDao = groceryItemDao;
        }

        @Override
        protected Void doInBackground(GroceryItem... groceryItems) {
            groceryItemDao.delete(groceryItems[0]);
            return null;
        }
    }

    private static class UpdateGroceryItemAsyncTask extends AsyncTask<GroceryItem, Void, Void> {
        private final GroceryItemDao groceryItemDao;

        private UpdateGroceryItemAsyncTask(GroceryItemDao groceryItemDao) {
            this.groceryItemDao = groceryItemDao;
        }

        @Override
        protected Void doInBackground(GroceryItem... groceryItems) {
            groceryItemDao.update(groceryItems[0]);
            return null;
        }
    }

    private static class AddGroceryAsyncTask extends AsyncTask<GroceryItem, Void, Void> {
        private final GroceryItemDao groceryItemDao;

        public AddGroceryAsyncTask(GroceryItemDao groceryItemDao) {
            this.groceryItemDao = groceryItemDao;
        }

        @Override
        protected Void doInBackground(GroceryItem... groceryItems) {
            groceryItemDao.insert(groceryItems[0]);
            return null;
        }
    }

    private static class clearListAsyncTask extends AsyncTask<Void, Void, Void> {
        private final GroceryItemDao groceryItemDao;

        private clearListAsyncTask(GroceryItemDao groceryItemDao) {
            this.groceryItemDao = groceryItemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            groceryItemDao.clearList();
            return null;
        }
    }


}
