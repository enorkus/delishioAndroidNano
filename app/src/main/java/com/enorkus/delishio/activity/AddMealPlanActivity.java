package com.enorkus.delishio.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.enorkus.delishio.R;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.fragment.AddMealPlanFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMealPlanActivity extends AppCompatActivity {

    @BindView(R.id.addMealPlanSaveBtn)
    protected FloatingActionButton addMealPlanSaveBtn;

    private List<Meal> selectedMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_plan);
        ButterKnife.bind(this);
        setupLayout();
    }

    private void setupLayout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.addMealPlanFragmentContainer, new AddMealPlanFragment()).commit();
    }

    public void setFABOnClickListener(View.OnClickListener listener) {
        addMealPlanSaveBtn.setOnClickListener(listener);
    }

    public List<Meal> getSelectedMeals() {
        return selectedMeals;
    }

    public void setSelectedMeals(List<Meal> selectedMeals) {
        this.selectedMeals = selectedMeals;
    }
}
