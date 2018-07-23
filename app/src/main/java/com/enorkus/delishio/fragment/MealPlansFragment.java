package com.enorkus.delishio.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealActivity;
import com.enorkus.delishio.activity.AddMealPlanActivity;
import com.enorkus.delishio.adapter.MealListAdapter;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Meal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealPlansFragment extends Fragment {

    @BindView(R.id.mealPlansRecyclerView)
    protected RecyclerView mealPlansRecyclerView;
    @BindView(R.id.mealPlansFab)
    protected FloatingActionButton fab;

    public MealPlansFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_plans, container, false);
        ButterKnife.bind(this, rootView);

        mealPlansRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        MealContentProviderHelper helper = new MealContentProviderHelper(getContext());
//        List<MealPlan> mealPlans = helper.fetchAllMeals();
//        RecyclerView.Adapter adapter = new MealListAdapter(mealPlans, getContext());
//        mealPlansRecyclerView.setAdapter(adapter);

        fab.setImageResource(R.drawable.ic_add_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMealPlanActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
