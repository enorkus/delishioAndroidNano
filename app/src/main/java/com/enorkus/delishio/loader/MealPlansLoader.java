package com.enorkus.delishio.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.MealPlan;

import java.util.List;

public class MealPlansLoader extends AsyncTaskLoader<List<MealPlan>> {

    private MealContentProviderHelper contentHelper = new MealContentProviderHelper(getContext());

    public MealPlansLoader(Context context) {
        super(context);
    }

    @Override
    public List<MealPlan> loadInBackground() {
        return contentHelper.fetchAllMealPlans();
    }
}
