package com.enorkus.delishio.listener;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.enorkus.delishio.R;
import com.enorkus.delishio.activity.AddMealActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealImageClickListener implements View.OnClickListener {

    private AddMealActivity ctx;

    public MealImageClickListener(AddMealActivity ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {

        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.meal_picture_choose_dialog);
        Button chooseImageGaleryBtn = dialog.findViewById(R.id.chooseImageGalery);
        chooseImageGaleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                ctx.startActivityForResult(Intent.createChooser(intent, "Select Picture"), AddMealActivity.PICK_IMAGE_REQUEST);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
