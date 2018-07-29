package com.enorkus.delishio.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.MealDetailsActivity;
import com.enorkus.delishio.data.DatabaseContract;
import com.enorkus.delishio.entity.Ingredient;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.fragment.MealsListFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.ViewHolder> {

    private Context ctx;
    protected List<Meal> meals;

    public MealListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public MealListAdapter(List<Meal> meals, Context ctx) {
        this.meals = meals;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_item_meal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Meal meal = meals.get(position);
        holder.mealName.setText(meal.getName());
        loadImage(meal.getPicturePath(), holder.mealImage);
        holder.mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MealDetailsActivity.class);
                intent.putExtra(MealsListFragment.EXTRA_MEAL, meal);
                view.getContext().startActivity(intent);
            }
        });
    }

    protected void loadImage(String picturePath, ImageView mealImageView) {
            String dir = picturePath.substring(0, picturePath.lastIndexOf("/"));
            String pictureName = picturePath.substring(picturePath.lastIndexOf("/") + 1, picturePath.lastIndexOf("."));
            File file = new File(dir, pictureName);
            Picasso.with(ctx)
                    .load(file)
                    .placeholder(R.drawable.meal_default)
                    .error(R.drawable.meal_default)
                    .into(mealImageView);
    }

    @Override
    public int getItemCount() {
        if(null == meals) {
            return 0;
        }
        return meals.size();
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mealName;
        public ImageView mealImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.listItemMeal);
            mealImage = itemView.findViewById(R.id.mealsListMealImage);
        }
    }
}
