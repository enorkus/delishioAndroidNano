package com.enorkus.delishio.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enorkus.delishio.R;
import com.enorkus.delishio.listener.AddMealIngredientClickListener;
import com.enorkus.delishio.listener.MealImageClickListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMealActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    public static final int SEARCH_IMAGE = 2;
    @BindView(R.id.addMealIngredientButton)
    protected Button addIngredientBtn;
    @BindView(R.id.ingredientsLinearLayout)
    protected LinearLayout ingredientsLayout;
    @BindView(R.id.mealPicture)
    protected ImageView mealPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        ButterKnife.bind(this);
        setupLayout();
    }

    private void setupLayout() {
        addIngredientBtn.setOnClickListener(new AddMealIngredientClickListener(this));
        mealPicture.setOnClickListener(new MealImageClickListener(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mealPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
