package com.enorkus.delishio.listener;

import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealActivity;

import java.util.ArrayList;
import java.util.List;

public class AddMealIngredientClickListener implements View.OnClickListener {

    private AddMealActivity ctx;
    private List<EditText> ingredientNames;
    private List<View> ingredientRows;

    public AddMealIngredientClickListener(AddMealActivity ctx) {
        ingredientNames = new ArrayList<>();
        ingredientRows = new ArrayList<>();
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {
        final LinearLayout mealIngredientsLinearLayout = ctx.findViewById(R.id.ingredientsLinearLayout);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View newIngredientInputRow  = inflater.inflate(R.layout.add_ingredient_row, mealIngredientsLinearLayout, false);

        final Spinner ingredientUnitSpinner = newIngredientInputRow.findViewById(R.id.ingredientRowUnitsSpinner);
        ingredientUnitSpinner.setSelection(0, true);
        final View spinnerView = ingredientUnitSpinner.getSelectedView();
        ((TextView)spinnerView).setTextColor(ctx.getResources().getColor(R.color.gray));

        ingredientUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(ingredientUnitSpinner.getSelectedItem().toString().equals("no unit")) {
                    ((TextView) view).setTextColor(ctx.getResources().getColor(R.color.gray));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ingredientRows.add(newIngredientInputRow);

        FloatingActionButton removeIngredientBtn = newIngredientInputRow.findViewById(R.id.removeIngredientBtn);
        removeIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout parentLayout = (LinearLayout) view.getParent();
                mealIngredientsLinearLayout.removeView(parentLayout);
                for(View ingredientRow : ingredientRows) {
                    if(ingredientRow.getId() == parentLayout.getId()) {
                        ingredientRows.remove(ingredientRow);
                        break;
                    }
                }

            }
        });
        mealIngredientsLinearLayout.addView(newIngredientInputRow);
    }

    public List<EditText> getIngredientNames() {
        return ingredientNames;
    }

    public List<View> getIngredientRows() {
        return ingredientRows;
    }
}
