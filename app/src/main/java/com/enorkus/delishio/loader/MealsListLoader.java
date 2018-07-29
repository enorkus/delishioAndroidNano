package com.enorkus.delishio.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Meal;

import java.util.List;

public class MealsListLoader extends AsyncTaskLoader<List<Meal>> {

    private MealContentProviderHelper contentHelper = new MealContentProviderHelper(getContext());

    public MealsListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Meal> loadInBackground() {
        return contentHelper.fetchAllMeals();
    }
}
