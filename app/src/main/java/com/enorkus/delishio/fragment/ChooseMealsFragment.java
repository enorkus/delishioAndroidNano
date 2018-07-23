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
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Meal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseMealsFragment extends Fragment {

    @BindView(R.id.chooseMeals_mealsList)
    protected RecyclerView chooseMealsList;

    private AddMealPlanActivity activity;

    public ChooseMealsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choose_meals, container, false);
        ButterKnife.bind(this, rootView);

        activity = (AddMealPlanActivity) getActivity();
        activity.setFABOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        MealContentProviderHelper helper = new MealContentProviderHelper(getContext());
        List<Meal> meals = helper.fetchAllMeals();
        chooseMealsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        chooseMealsList.setAdapter(new ChooseMealsListAdapter(meals, (AddMealPlanActivity) getActivity()));
        return rootView;
    }

}
