package com.enorkus.delishio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enorkus.delishio.R;
import com.enorkus.delishio.entity.Meal;

import java.util.List;

public class AddMealPlanMealListAdapter extends MealListAdapter {
    public AddMealPlanMealListAdapter(List<Meal> meals, Context ctx) {
        super(meals, ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_item_meal_small, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}
