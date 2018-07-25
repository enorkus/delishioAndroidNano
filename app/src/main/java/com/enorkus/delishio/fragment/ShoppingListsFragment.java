package com.enorkus.delishio.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enorkus.delishio.R;
import com.enorkus.delishio.adapter.ShoppingListsAdapter;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.ShoppingList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListsFragment extends Fragment {

    @BindView(R.id.shoppingListRecyclerView)
    protected RecyclerView shoppingListRecyclerView;

    public ShoppingListsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        ButterKnife.bind(this, rootView);

        MealContentProviderHelper helper = new MealContentProviderHelper(getContext());
        List<ShoppingList> shoppingListLists = helper.fetchAllShoppingLists();

        shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingListRecyclerView.setAdapter(new ShoppingListsAdapter(shoppingListLists));
        return rootView;
    }

}
