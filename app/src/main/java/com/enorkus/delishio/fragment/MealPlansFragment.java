package com.enorkus.delishio.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enorkus.delishio.MainActivity;
import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealPlanActivity;
import com.enorkus.delishio.adapter.MealPlanListAdapter;
import com.enorkus.delishio.entity.MealPlan;
import com.enorkus.delishio.loader.MealPlansLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealPlansFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MealPlan>> {

    @BindView(R.id.mealPlansRecyclerView)
    protected RecyclerView mealPlansRecyclerView;

    private MealPlanListAdapter adapter;

    public MealPlansFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_plans, container, false);
        ButterKnife.bind(this, rootView);

        mealPlansRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MealPlanListAdapter(getContext());
        mealPlansRecyclerView.setAdapter(adapter);
        getActivity().getSupportLoaderManager().initLoader(0, null, this).forceLoad();

        FloatingActionButton fab = ((MainActivity)getActivity()).getFAB();
        fab.setImageResource(R.drawable.icon_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMealPlanActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Loader<List<MealPlan>> onCreateLoader(int id, Bundle args) {
        return new MealPlansLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<MealPlan>> loader, List<MealPlan> data) {
        adapter.setMealPlans(data);
    }

    @Override
    public void onLoaderReset(Loader<List<MealPlan>> loader) {
        adapter.setMealPlans(new ArrayList<MealPlan>());
    }
}
