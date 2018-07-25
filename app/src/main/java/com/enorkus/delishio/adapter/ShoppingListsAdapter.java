package com.enorkus.delishio.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.ShoppingListDetailsActivity;
import com.enorkus.delishio.entity.Ingredient;
import com.enorkus.delishio.entity.ShoppingList;

import java.util.List;

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder> {

    private List<ShoppingList> shoppingListLists;

    public ShoppingListsAdapter(List<ShoppingList> shoppingListLists) {
        this.shoppingListLists = shoppingListLists;
    }

    @Override
    public ShoppingListsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_item_shopping_list, parent, false);
        ShoppingListsAdapter.ViewHolder viewHolder = new ShoppingListsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ShoppingList shoppingList = shoppingListLists.get(position);
        holder.shoppingListName.setText(shoppingList.getName());
        holder.shoppingListName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShoppingListDetailsActivity.class);
                intent.putExtra(ShoppingListDetailsActivity.EXTRA_SHOPPING_LIST, shoppingList);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingListLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView shoppingListName;

        public ViewHolder(View itemView) {
            super(itemView);
            shoppingListName = itemView.findViewById(R.id.listItemShoppingList_name);
        }
    }
}
