package com.enorkus.delishio;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.enorkus.delishio.adapter.MealsListAdapter;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.fragment.MealsListFragment;
import com.enorkus.delishio.listener.BottomNavigationItemSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottomNavigation)
    protected BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createLaytout();
    }

    private void createLaytout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new MealsListFragment()).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationItemSelectListener(fragmentManager));
    }
}
