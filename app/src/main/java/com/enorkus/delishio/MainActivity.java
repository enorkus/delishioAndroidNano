package com.enorkus.delishio;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.enorkus.delishio.activity.ShoppingListDetailsActivity;
import com.enorkus.delishio.fragment.MealsListFragment;
import com.enorkus.delishio.fragment.ShoppingListsFragment;
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
        boolean isFromGenerateShoppingList = getIntent().getBooleanExtra(ShoppingListDetailsActivity.EXTRA_SHOPPING_LIST, false);
        if(isFromGenerateShoppingList) {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ShoppingListsFragment()).commit();
            bottomNavigation.setSelectedItemId(R.id.menuShoppingList);
            fab.setVisibility(View.GONE);
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new MealsListFragment()).commit();
        }
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationItemSelectListener(fragmentManager, fab));

    }

    public FloatingActionButton getFAB() {
        return fab;
    }
}
