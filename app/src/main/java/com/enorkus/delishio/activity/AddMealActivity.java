package com.enorkus.delishio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.enorkus.delishio.R;
import com.enorkus.delishio.listener.AddMealIngredientClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMealActivity extends AppCompatActivity {

    @BindView(R.id.addMealIngredientButton)
    protected Button addIngredientBtn;
    @BindView(R.id.ingredientsLinearLayout)
    protected LinearLayout ingredientsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        ButterKnife.bind(this);
        setupLayout();
    }

    private void setupLayout() {
        addIngredientBtn.setOnClickListener(new AddMealIngredientClickListener(this));
    }
}
