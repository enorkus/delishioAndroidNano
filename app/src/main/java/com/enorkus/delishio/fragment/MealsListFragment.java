package com.enorkus.delishio.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealActivity;
import com.enorkus.delishio.adapter.MealListAdapter;
import com.enorkus.delishio.data.DatabaseContract;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Meal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealsListFragment extends Fragment {

    public static final String EXTRA_MEAL = MealsListFragment.class.getSimpleName() + "_EXTRA_MEAL";

    @BindView(R.id.mealsListRecyclerView)
    protected RecyclerView mealsListRecyclerView;
    @BindView(R.id.mealsListFab)
    protected FloatingActionButton fab;

    public MealsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meals_list, container, false);
        ButterKnife.bind(this, rootView);

        mealsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MealContentProviderHelper helper = new MealContentProviderHelper(getContext());
        List<Meal> meals = helper.fetchAllMeals();
        RecyclerView.Adapter adapter = new MealListAdapter(meals, getContext());
        mealsListRecyclerView.setAdapter(adapter);

        fab.setImageResource(R.drawable.ic_add_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMealActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
