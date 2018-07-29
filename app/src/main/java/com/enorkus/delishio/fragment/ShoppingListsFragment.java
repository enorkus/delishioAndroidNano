package com.enorkus.delishio.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enorkus.delishio.R;
import com.enorkus.delishio.adapter.ShoppingListAdapter;
import com.enorkus.delishio.adapter.ShoppingListsAdapter;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.MealPlan;
import com.enorkus.delishio.entity.ShoppingList;
import com.enorkus.delishio.loader.ShoppingListsLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ShoppingList>> {

    @BindView(R.id.shoppingListRecyclerView)
    protected RecyclerView shoppingListRecyclerView;

    private ShoppingListsAdapter adapter;

    public ShoppingListsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        ButterKnife.bind(this, rootView);

        MealContentProviderHelper helper = new MealContentProviderHelper(getContext());
        List<ShoppingList> shoppingListLists = helper.fetchAllShoppingLists();

        adapter = new ShoppingListsAdapter(shoppingListLists);
        shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingListRecyclerView.setAdapter(adapter);
        getActivity().getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        return rootView;
    }

    @Override
    public Loader<List<ShoppingList>> onCreateLoader(int id, Bundle args) {
        return new ShoppingListsLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<ShoppingList>> loader, List<ShoppingList> data) {
        adapter.setShoppingListLists(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ShoppingList>> loader) {
        adapter.setShoppingListLists(new ArrayList<ShoppingList>());
    }
}
