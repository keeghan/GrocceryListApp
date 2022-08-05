package com.example.groceryListApp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryListApp.DatabaseResources.GroceryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryHolder> {
    private List<GroceryItem> groceryItems = new ArrayList<>();
    private OnClickListener mListener;

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public GroceryAdapter.GroceryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_item, parent, false);

        return new GroceryHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryAdapter.GroceryHolder holder, int position) {
        GroceryItem currentItem = groceryItems.get(position);
        holder.name.setText(currentItem.getName());
        holder.amount.setText(String.format(Locale.getDefault(), "%d", currentItem.getAmount()));
        holder.price.setText(String.format(Locale.getDefault(), "%.1f", currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    public void setGroceryItems(List<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems;
        notifyDataSetChanged();
    }

    public GroceryItem getGroceryItemAt(int position) {
        return groceryItems.get(position);
    }


    static class GroceryHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView amount;
        private final TextView price;

        public GroceryHolder(@NonNull View itemView, final OnClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.groceryItemName);
            amount = itemView.findViewById(R.id.groceryItemAmount);
            price = itemView.findViewById(R.id.groceryItemPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
