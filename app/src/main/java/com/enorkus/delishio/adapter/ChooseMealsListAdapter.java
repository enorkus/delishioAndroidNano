package com.enorkus.delishio.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;

import com.enorkus.delishio.activity.AddMealPlanActivity;
import com.enorkus.delishio.activity.MealDetailsActivity;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.fragment.MealsListFragment;

import java.util.ArrayList;
import java.util.List;

public class ChooseMealsListAdapter extends MealListAdapter {

    private AddMealPlanActivity ctx;

    public ChooseMealsListAdapter(List<Meal> meals, AddMealPlanActivity ctx) {
        super(meals, ctx);
        this.ctx = ctx;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Meal meal = meals.get(position);
        holder.mealName.setText(meal.getName());
        super.loadImage(meal.getPicturePath(), holder.mealImage);

        if(!ctx.getSelectedMeals().contains(meal)) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);  //0 means grayscale
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            holder.mealImage.setColorFilter(cf);
            holder.mealImage.setImageAlpha(128);   // 128 = 0.5
        } else {
            holder.mealImage.setColorFilter(null);
            holder.mealImage.setImageAlpha(255);
        }

        holder.mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ctx.getSelectedMeals().contains(meal)) {
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(0);  //0 means grayscale
                    ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
                    holder.mealImage.setColorFilter(cf);
                    holder.mealImage.setImageAlpha(128);   // 128 = 0.5
                    ctx.getSelectedMeals().remove(meal);
                } else {
                    holder.mealImage.setColorFilter(null);
                    holder.mealImage.setImageAlpha(255);
                    ctx.getSelectedMeals().add(meal);
                }
            }
        });
    }
}
