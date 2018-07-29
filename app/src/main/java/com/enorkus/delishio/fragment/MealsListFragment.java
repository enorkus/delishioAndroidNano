package com.enorkus.delishio.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enorkus.delishio.MainActivity;
import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealActivity;
import com.enorkus.delishio.adapter.MealListAdapter;
import com.enorkus.delishio.data.DatabaseContract;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.entity.MealPlan;
import com.enorkus.delishio.loader.MealsListLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Meal>> {

    public static final String EXTRA_MEAL = MealsListFragment.class.getSimpleName() + "_EXTRA_MEAL";

    @BindView(R.id.mealsListRecyclerView)
    protected RecyclerView mealsListRecyclerView;

    private MealListAdapter adapter;

    public MealsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meals_list, container, false);
        ButterKnife.bind(this, rootView);

        mealsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MealListAdapter(getContext());
        mealsListRecyclerView.setAdapter(adapter);
        getActivity().getSupportLoaderManager().initLoader(2, null, this).forceLoad();

        FloatingActionButton fab = ((MainActivity)getActivity()).getFAB();
        fab.setImageResource(R.drawable.icon_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMealActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public Loader<List<Meal>> onCreateLoader(int id, Bundle args) {
        return new MealsListLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Meal>> loader, List<Meal> data) {
        adapter.setMeals(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Meal>> loader) {
        adapter.setMeals(new ArrayList<Meal>());
    }
}
