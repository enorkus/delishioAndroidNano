package com.enorkus.delishio.listener;

import android.app.Dialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealActivity;
import com.enorkus.delishio.activity.SearchMealImageActivity;

public class MealImageClickListener implements View.OnClickListener {

    private AddMealActivity ctx;

    public MealImageClickListener(AddMealActivity ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {


        LayoutInflater factory = LayoutInflater.from(ctx);
        final View dialogView = factory.inflate(R.layout.meal_picture_choose_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(ctx).setView(dialogView).create();
        ConstraintLayout searchInternetLayout = dialogView.findViewById(R.id.choosePicDialog_searchInternetLayout);
        ConstraintLayout searchGaleryLayout = dialogView.findViewById(R.id.choosePicDialog_searchGaleryLayout);
        searchGaleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                ctx.startActivityForResult(Intent.createChooser(intent, ctx.getResources().getString(R.string.select_picture)), AddMealActivity.PICK_IMAGE);
                dialog.dismiss();
            }
        });

        searchInternetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, SearchMealImageActivity.class);
                ctx.startActivityForResult(intent, AddMealActivity.SEARCH_IMAGE);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
