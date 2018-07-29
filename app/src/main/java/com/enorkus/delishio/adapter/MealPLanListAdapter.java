package com.enorkus.delishio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enorkus.delishio.MainActivity;
import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.MealPlanDetailsActivity;
import com.enorkus.delishio.entity.MealPlan;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MealPLanListAdapter extends RecyclerView.Adapter<MealPLanListAdapter.ViewHolder> {

    private List<MealPlan> mealPlans;
    private Context ctx;

    public MealPLanListAdapter(List<MealPlan> mealPlans, Context ctx) {
        this.mealPlans = mealPlans;
        this.ctx = ctx;
    }

    @Override
    public MealPLanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_meal_plan, parent, false);
        MealPLanListAdapter.ViewHolder viewHolder = new MealPLanListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MealPLanListAdapter.ViewHolder holder, int position) {
        final MealPlan mealPlan = mealPlans.get(position);
        holder.mealPlanName.setText(mealPlan.getName());
        holder.mealPlansListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, MealPlanDetailsActivity.class);
                intent.putExtra(MealPlanDetailsActivity.EXTRA_MEAL_PLAN, mealPlan);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public void setMealPlans(List<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mealPlanName;
        private CardView mealPlansListCard;
        private ImageView mealPlanImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mealPlanName = itemView.findViewById(R.id.mealPlans_mealPlanName);
            mealPlansListCard = itemView.findViewById(R.id.mealPlansListCard);
            mealPlanImage = itemView.findViewById(R.id.mealPlans_mealImage);
        }
    }
}
