package com.enorkus.delishio.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enorkus.delishio.R;
import com.enorkus.delishio.data.DatabaseContract;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.listener.AddMealIngredientClickListener;
import com.enorkus.delishio.listener.MealImageClickListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.enorkus.delishio.data.DatabaseContract.*;

public class AddMealActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    public static final int SEARCH_IMAGE = 2;
    @BindView(R.id.addMealIngredientButton)
    protected Button addIngredientBtn;
    @BindView(R.id.ingredientsLinearLayout)
    protected LinearLayout ingredientsLayout;
    @BindView(R.id.mealPicture)
    protected ImageView mealPicture;
    @BindView(R.id.mealNameInput)
    protected TextView mealNameInput;
    @BindView(R.id.addMealSaveBtn)
    protected Button saveMealBtn;
    @BindView(R.id.addMealCancelBtn)
    protected Button cancelBtn;

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
        saveMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap mealPic = ((BitmapDrawable)mealPicture.getDrawable()).getBitmap();
                Long currentTime = System.currentTimeMillis()/1000;
                String picName = currentTime.toString();

                //Store image
                String picturePath = saveToInternalStorage(mealPic, picName) + "/" +picName + ".png";
                //Store image

                MealContentProviderHelper helper = new MealContentProviderHelper(view.getContext());
                helper.saveMeal(new Meal(mealNameInput.getText().toString(), picturePath, null));
            }
        });
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
        } else if(requestCode == SEARCH_IMAGE && resultCode == RESULT_OK) {
            String pictureURL = data.getStringExtra(SearchMealImageActivity.EXTRA_MEAL_PICTURE_URL);
            Picasso.with(this)
                    .load(pictureURL)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(mealPicture);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String name){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File picPath = new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(picPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
