package com.enorkus.delishio.listener;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.enorkus.delishio.R;
import com.enorkus.delishio.fragment.MealPlansFragment;
import com.enorkus.delishio.fragment.MealsListFragment;
import com.enorkus.delishio.fragment.ShoppingListsFragment;

public class BottomNavigationItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;

    public BottomNavigationItemSelectListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment selectedFragment = null;
        if(id == R.id.menuMeal) {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new MealsListFragment()).commit();
            return true;
        } else if(id == R.id.menuMealPlan) {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new MealPlansFragment()).commit();
            return true;
        } else if(id == R.id.menuShoppingList) {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ShoppingListsFragment()).commit();
            return true;
        }
        return false;
    }
}
