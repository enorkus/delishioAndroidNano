package com.enorkus.delishio;

import android.os.PersistableBundle;
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

    private static final String BOTTOM_NAVIGATION_SELECTED_ID = MainActivity.class.getName() + ".bottomNavSelectedId";
    @BindView(R.id.bottomNavigation)
    protected BottomNavigationView bottomNavigation;
    @BindView(R.id.mainActivity_fab)
    protected FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createLaytout(savedInstanceState);
    }

    private void createLaytout(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(savedInstanceState != null) {
            bottomNavigation.setSelectedItemId(savedInstanceState.getInt(BOTTOM_NAVIGATION_SELECTED_ID));
        } else {
            boolean isFromGenerateShoppingList = getIntent().getBooleanExtra(ShoppingListDetailsActivity.EXTRA_SHOPPING_LIST, false);
            if(isFromGenerateShoppingList) {
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ShoppingListsFragment()).commit();
                bottomNavigation.setSelectedItemId(R.id.menuShoppingList);
                fab.setVisibility(View.GONE);
            } else {
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new MealsListFragment()).commit();
            }
        }
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationItemSelectListener(fragmentManager, fab));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BOTTOM_NAVIGATION_SELECTED_ID, bottomNavigation.getSelectedItemId());

    }

    public FloatingActionButton getFAB() {
        return fab;
    }
}
