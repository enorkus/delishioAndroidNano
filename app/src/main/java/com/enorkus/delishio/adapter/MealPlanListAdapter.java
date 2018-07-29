package com.enorkus.delishio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.MealPlanDetailsActivity;
import com.enorkus.delishio.entity.MealPlan;

import java.util.List;

public class MealPlanListAdapter extends RecyclerView.Adapter<MealPlanListAdapter.ViewHolder> {

    private List<MealPlan> mealPlans;
    private Context ctx;

    public MealPlanListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public MealPlanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_meal_plan, parent, false);
        MealPlanListAdapter.ViewHolder viewHolder = new MealPlanListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MealPlanListAdapter.ViewHolder holder, int position) {
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
        if(null == mealPlans) {
            return 0;
        }
        return mealPlans.size();
    }

    public void setMealPlans(List<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mealPlanName;
        private CardView mealPlansListCard;

        public ViewHolder(View itemView) {
            super(itemView);
            mealPlanName = itemView.findViewById(R.id.mealPlans_mealPlanName);
            mealPlansListCard = itemView.findViewById(R.id.mealPlansListCard);
        }
    }
}
