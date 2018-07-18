package com.enorkus.delishio.listener;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealActivity;

public class AddMealIngredientClickListener implements View.OnClickListener {

    private AddMealActivity ctx;
    private LinearLayout ingredientsLayout;

    public AddMealIngredientClickListener(AddMealActivity ctx, LinearLayout ingredientsLayout) {
        this.ctx = ctx;
        this.ingredientsLayout = ingredientsLayout;
    }

    @Override
    public void onClick(View view) {
        LinearLayout ingredientLinearLayout = new LinearLayout(ctx);
        TextInputLayout ingredientInputLayout = new TextInputLayout(ctx);
        TextInputEditText ingredientEditText = new TextInputEditText (ctx);
        FloatingActionButton removeIngredientBtn = new FloatingActionButton(ctx);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams fabLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3.0f);

        removeIngredientBtn.setId(View.generateViewId());
        removeIngredientBtn.setLayoutParams(fabLayoutParams);
        removeIngredientBtn.setClickable(true);
        removeIngredientBtn.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            removeIngredientBtn.setElevation(0);
        }
        removeIngredientBtn.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_remove_black_24dp));

        ingredientEditText.setLayoutParams(layoutParams);
        ingredientLinearLayout.setLayoutParams(layoutParams);
        ingredientLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        layoutParams.weight = 7.0f;
        ingredientInputLayout.setLayoutParams(layoutParams);
        ingredientInputLayout.setHint(ctx.getResources().getString(R.string.meal_ingredient));

        ingredientInputLayout.addView(ingredientEditText);
        ingredientLinearLayout.addView(ingredientInputLayout, layoutParams);
        ingredientLinearLayout.addView(removeIngredientBtn, layoutParams);
        //This only works after fab is added to the view.
        ((LinearLayout.LayoutParams)removeIngredientBtn.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        ingredientsLayout.addView(ingredientLinearLayout, layoutParams);

    }
}
