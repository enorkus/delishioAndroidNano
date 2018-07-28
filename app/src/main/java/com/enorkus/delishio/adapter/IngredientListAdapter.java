package com.enorkus.delishio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.entity.Ingredient;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {

    private Context ctx;
    private List<Ingredient> ingredients;

    public IngredientListAdapter(Context ctx, List<Ingredient> ingredients) {
        this.ctx = ctx;
        this.ingredients = ingredients;
    }

    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_item_ingredient, parent, false);
        IngredientListAdapter.ViewHolder viewHolder = new IngredientListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientListAdapter.ViewHolder holder, int position) {
        final Ingredient ingredient = ingredients.get(position);
        holder.ingredientQuantity.setText(String.valueOf(ingredient.getQuantity()) + " ");
        holder.ingredientUnit.setText(ingredient.getUnit() + " ");
        holder.ingredientName.setText(" " + ingredient.getName());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientQuantity;
        private TextView ingredientUnit;
        private TextView ingredientName;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientQuantity = itemView.findViewById(R.id.listItemIngredient_quantity);
            ingredientUnit = itemView.findViewById(R.id.listItemIngredient_unit);
            ingredientName = itemView.findViewById(R.id.listItemIngredient_name);
        }
    }
}
