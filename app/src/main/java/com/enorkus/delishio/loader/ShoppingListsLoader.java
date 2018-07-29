package com.enorkus.delishio.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.ShoppingList;

import java.util.List;

public class ShoppingListsLoader extends AsyncTaskLoader<List<ShoppingList>> {

    private MealContentProviderHelper contentHelper = new MealContentProviderHelper(getContext());;

    public ShoppingListsLoader(Context context) {
        super(context);
    }

    @Override
    public List<ShoppingList> loadInBackground() {
        return contentHelper.fetchAllShoppingLists();
    }
}
