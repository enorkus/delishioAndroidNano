package com.enorkus.delishio.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.entity.Ingredient;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<Ingredient> ingredients;

    public ShoppingListAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_item_grocery, parent, false);
        ShoppingListAdapter.ViewHolder viewHolder = new ShoppingListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Ingredient ingredient = ingredients.get(position);
        String groceryItem = String.valueOf(ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getName());
        holder.groceryItem.setText(groceryItem);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView groceryItem;

        public ViewHolder(View itemView) {
            super(itemView);
            groceryItem = itemView.findViewById(R.id.listItemGrocery_groceryItem);
        }
    }
}
