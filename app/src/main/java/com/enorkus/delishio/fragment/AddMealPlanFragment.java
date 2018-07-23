package com.enorkus.delishio.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealPlanActivity;
import com.enorkus.delishio.adapter.ChooseMealsListAdapter;
import com.enorkus.delishio.entity.Meal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMealPlanFragment extends Fragment {

    @BindView(R.id.chooseMealsBtn)
    protected Button chooseMealsBtn;
    @BindView(R.id.addMealPlan_selectedMeals)
    protected RecyclerView selectedMealsList;

    private AddMealPlanActivity activity;

    public AddMealPlanFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_meal_plan, container, false);
        ButterKnife.bind(this, rootView);

        selectedMealsList.setLayoutManager(new LinearLayoutManager(activity));
        selectedMealsList.setAdapter(new ChooseMealsListAdapter(activity != null ? activity.getSelectedMeals() : new ArrayList<Meal>(), activity));

        activity = (AddMealPlanActivity) getActivity();
        activity.setFABOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save meal plan
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
