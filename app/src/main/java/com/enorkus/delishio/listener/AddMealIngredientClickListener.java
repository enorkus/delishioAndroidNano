package com.enorkus.delishio.listener;

import android.view.LayoutInflater;
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
        LinearLayout layout = ctx.findViewById(R.id.ingredientsLinearLayout);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View newRow  = inflater.inflate(R.layout.add_ingredient_row, layout, false);
        layout.addView(newRow);
    }
}
