package com.enorkus.delishio.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.adapter.IngredientListAdapter;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.fragment.MealsListFragment;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealDetailsActivity extends AppCompatActivity {

    @BindView(R.id.mealDetails_mealPicture)
    protected ImageView mealPicture;
    @BindView(R.id.mealDetails_ingredientList)
    protected RecyclerView ingredientList;
    @BindView(R.id.mealDetails_mealNameToolbar)
    protected Toolbar toolbar;
    @BindView(R.id.mealDetails_coolapsingToolbar)
    protected CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        ButterKnife.bind(this);

        Meal meal = getIntent().getParcelableExtra(MealsListFragment.EXTRA_MEAL);
        String picturePath = meal.getPicturePath();
        String dir = picturePath.substring(0, picturePath.lastIndexOf("/"));
        String pictureName = picturePath.substring(picturePath.lastIndexOf("/") + 1, picturePath.lastIndexOf("."));
        File file = new File(dir, pictureName);
        Picasso.with(this)
                .load(file)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(mealPicture);

        collapsingToolbarLayout.setTitle(meal.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ingredientList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new IngredientListAdapter(this, meal.getIngredients());
        ingredientList.setAdapter(adapter);
    }
}
