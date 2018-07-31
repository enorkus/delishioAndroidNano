package com.enorkus.delishio.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.enorkus.delishio.MainActivity;
import com.enorkus.delishio.R;
import com.enorkus.delishio.adapter.ShoppingListAdapter;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Ingredient;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.entity.MealPlan;
import com.enorkus.delishio.entity.ShoppingList;
import com.enorkus.delishio.widget.DelishioWidgetProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SHOPPING_LIST = ShoppingListDetailsActivity.class.getName() + ".extra.shopping.list";

    @BindView(R.id.shoppingListDetails_coordinatorLayout)
    protected CoordinatorLayout coordinatorLayout;
    @BindView(R.id.shoppingList)
    protected RecyclerView shoppingListRecyclerView;
    @BindView(R.id.shoppingListDetailsFAB)
    protected FloatingActionButton shoppingListDetailsFAB;
    @BindView(R.id.shoppingListDetail_shoppingListName)
    protected EditText shoppingListName;
    @BindView(R.id.shoppingListDetail_shoppingListNameTV)
    protected TextView shoppingListNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_details);
        ButterKnife.bind(this);
        setupLayout();
    }

    private void setupLayout() {
        final ShoppingList shoppingList = getIntent().getParcelableExtra(EXTRA_SHOPPING_LIST);
        if(shoppingList != null) {
            shoppingListName.setVisibility(View.GONE);
            shoppingListDetailsFAB.setImageResource(R.drawable.icon_widget);
            shoppingListDetailsFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent)));
            shoppingListDetailsFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(view.getContext());
                    int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(view.getContext(), DelishioWidgetProvider.class));
                    DelishioWidgetProvider.updateAppWidget(view.getContext(), appWidgetManager, widgetIds, shoppingList.getIngredients());
                    Snackbar.make(coordinatorLayout, R.string.shopping_list_added_to_widget, Snackbar.LENGTH_LONG).show();
                }
            });
            shoppingListNameTV.setText(shoppingList.getName());
            final List<Ingredient> allIngredients = new ArrayList<>();
            shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            shoppingListRecyclerView.setAdapter(new ShoppingListAdapter(shoppingList.getIngredients()));
        } else {
            shoppingListNameTV.setVisibility(View.GONE);
            MealPlan mealPlan = getIntent().getParcelableExtra(MealPlanDetailsActivity.EXTRA_MEAL_PLAN);
            final List<Ingredient> allIngredients = new ArrayList<>();
            for(Meal meal : mealPlan.getMeals()) {
                allIngredients.addAll(meal.getIngredients());
            }
            shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            shoppingListRecyclerView.setAdapter(new ShoppingListAdapter(allIngredients));

            shoppingListDetailsFAB.setImageResource(R.drawable.icon_add);
            shoppingListDetailsFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            shoppingListDetailsFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShoppingList shoppingList = new ShoppingList(shoppingListName.getText().toString(), allIngredients);
                    MealContentProviderHelper helper = new MealContentProviderHelper(view.getContext());
                    helper.saveShoppingList(shoppingList);
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra(EXTRA_SHOPPING_LIST, true);
                    startActivity(intent);
                }
            });
        }

    }
}
