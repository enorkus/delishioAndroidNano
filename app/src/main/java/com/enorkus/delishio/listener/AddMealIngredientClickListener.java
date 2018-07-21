package com.enorkus.delishio.listener;

import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealActivity;

public class AddMealIngredientClickListener implements View.OnClickListener {

    private AddMealActivity ctx;

    public AddMealIngredientClickListener(AddMealActivity ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {
        final LinearLayout mealIngredientslinearLayout = ctx.findViewById(R.id.ingredientsLinearLayout);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View newIngredientInput  = inflater.inflate(R.layout.add_ingredient_row, mealIngredientslinearLayout, false);
//        FloatingActionButton removeIngredientBtn = newIngredientInput.findViewById(R.id.removeIngredientBtn);
//        removeIngredientBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LinearLayout parentLayout = (LinearLayout) view.getParent();
//                mealIngredientslinearLayout.removeView(parentLayout);
//            }
//        });
        mealIngredientslinearLayout.addView(newIngredientInput);
    }
}
