package com.enorkus.delishio.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.adapter.MealListAdapter;
import com.enorkus.delishio.entity.MealPlan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealPlanDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MEAL_PLAN = MealPlanDetailsActivity.class.getName() + ".extramealplan";

    @BindView(R.id.mealPlanDetails_meals)
    protected RecyclerView meals;
    @BindView(R.id.generateShoppingListFAB)
    protected FloatingActionButton generateShoppingListFAB;
    @BindView(R.id.mealPlanDetails_toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.mealPlanDetails_collapsingToolbar)
    protected CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_details);
        ButterKnife.bind(this);
        setupLayout();
    }

    private void setupLayout() {

        final MealPlan mealPlan = getIntent().getParcelableExtra(EXTRA_MEAL_PLAN);
        meals.setLayoutManager(new LinearLayoutManager(this));
        meals.setAdapter(new MealListAdapter(mealPlan.getMeals(), this));

        generateShoppingListFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShoppingListDetailsActivity.class);
                intent.putExtra(EXTRA_MEAL_PLAN, mealPlan);
                startActivity(intent);
            }
        });

        collapsingToolbarLayout.setTitleEnabled(false);
        toolbar.setTitle(mealPlan.getName());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
