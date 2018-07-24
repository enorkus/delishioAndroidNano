package com.enorkus.delishio.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealPlanActivity;
import com.enorkus.delishio.adapter.ChooseMealsListAdapter;
import com.enorkus.delishio.adapter.MealListAdapter;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.entity.MealPlan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMealPlanFragment extends Fragment {

    @BindView(R.id.chooseMealsBtn)
    protected Button chooseMealsBtn;
    @BindView(R.id.addMealPlan_selectedMeals)
    protected RecyclerView selectedMealsList;
    @BindView(R.id.addMealPlan_mealPlanName)
    protected EditText mealPlanName;

    private AddMealPlanActivity activity;

    public AddMealPlanFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_meal_plan, container, false);
        ButterKnife.bind(this, rootView);

        selectedMealsList.setLayoutManager(new LinearLayoutManager(activity));
        selectedMealsList.setAdapter(new MealListAdapter(activity != null ? activity.getSelectedMeals() : new ArrayList<Meal>(), activity));

        activity = (AddMealPlanActivity) getActivity();
        activity.setFABOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealPlan mealPlan = new MealPlan(mealPlanName.getText().toString(), activity.getSelectedMeals());
                MealContentProviderHelper helper = new MealContentProviderHelper(getContext());
                helper.saveMealPlan(mealPlan);
                getActivity().onBackPressed();
            }
        });

        chooseMealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.addMealPlanFragmentContainer, new ChooseMealsFragment()).addToBackStack(null).commit();
            }
        });
        return rootView;
    }

}
