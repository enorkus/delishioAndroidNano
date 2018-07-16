package com.enorkus.delishio.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enorkus.delishio.R;
import com.enorkus.delishio.adapter.MealsListAdapter;
import com.enorkus.delishio.entity.Meal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealsListFragment extends Fragment {

    @BindView(R.id.mealsListRecyclerView)
    protected RecyclerView mealsListRecyclerView;

    public MealsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meals_list, container, false);
        ButterKnife.bind(this, rootView);

        mealsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Meal> meals = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            Meal meal = new Meal("Meal Number " + i, null);
            meals.add(meal);
        }
        RecyclerView.Adapter adapter = new MealsListAdapter(meals);
        mealsListRecyclerView.setAdapter(adapter);

        return rootView;
    }

}
