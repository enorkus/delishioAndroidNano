package com.enorkus.delishio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.fragment.MealsListFragment;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealDetailsActivity extends AppCompatActivity {

    @BindView(R.id.mealDetails_mealPicture)
    protected ImageView mealPicture;
    @BindView(R.id.mealDetails_mealName)
    protected TextView mealName;

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

        mealName.setText(meal.getName());
    }
}
