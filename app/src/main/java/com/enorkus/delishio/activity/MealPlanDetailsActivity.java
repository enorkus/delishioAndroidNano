package com.enorkus.delishio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.adapter.MealListAdapter;
import com.enorkus.delishio.entity.MealPlan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealPlanDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MEAL_PLAN = MealPlanDetailsActivity.class.getName() + ".extramealplan";

    @BindView(R.id.mealPlanDetails_mealPlanName)
    protected TextView mealPlanName;
    @BindView(R.id.mealPlanDetails_meals)
    protected RecyclerView meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_details);
        ButterKnife.bind(this);
        setupLayout();
    }

    private void setupLayout() {
        MealPlan mealPlan = getIntent().getParcelableExtra(EXTRA_MEAL_PLAN);
        meals.setLayoutManager(new LinearLayoutManager(this));
        meals.setAdapter(new MealListAdapter(mealPlan.getMeals(), this));
    }
}
