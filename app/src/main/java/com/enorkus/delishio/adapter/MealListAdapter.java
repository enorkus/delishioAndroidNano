package com.enorkus.delishio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.MealDetailsActivity;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.fragment.MealsListFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.ViewHolder> {

    protected List<Meal> meals;
    private Context ctx;

    public MealListAdapter(List<Meal> meals, Context ctx) {
        this.ctx = ctx;
        this.meals = meals;
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
        return meals.size();
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
