package com.enorkus.delishio;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.enorkus.delishio.fragment.MealsListFragment;
import com.enorkus.delishio.listener.BottomNavigationItemSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottomNavigation)
    protected BottomNavigationView bottomNavigation;
    @BindView(R.id.mainActivity_fab)
    protected FloatingActionButton fab;

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

    public FloatingActionButton getFAB() {
        return fab;
    }
}
