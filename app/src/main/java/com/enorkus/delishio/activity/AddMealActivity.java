package com.enorkus.delishio.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.enorkus.delishio.MainActivity;
import com.enorkus.delishio.R;
import com.enorkus.delishio.data.MealContentProviderHelper;
import com.enorkus.delishio.entity.Ingredient;
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

public class AddMealActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    public static final int SEARCH_IMAGE = 2;
    @BindView(R.id.addMeal_addIngredientBtn)
    protected FloatingActionButton addIngredientBtn;
    @BindView(R.id.ingredientsLinearLayout)
    protected LinearLayout ingredientsLayout;
    @BindView(R.id.addMeal_saveMealfab)
    protected FloatingActionButton saveMealFAB;

    @BindView(R.id.mealPicture)
    protected ImageView mealPicture;
    @BindView(R.id.mealNameInput)
    protected EditText mealNameInput;
    @BindView(R.id.ingredienQuantity)
    protected EditText ingredientQuantityInput;
    @BindView(R.id.ingredientUnitsSpinner)
    protected Spinner ingredientUnitSpinner;
    @BindView(R.id.mealIngredientName0)
    protected EditText ingredientNameInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        ButterKnife.bind(this);
        setupLayout();
    }

    private void setupLayout() {
        final AddMealIngredientClickListener listener = new AddMealIngredientClickListener(this);
        addIngredientBtn.setOnClickListener(listener);
        mealPicture.setOnClickListener(new MealImageClickListener(this));
        saveMealFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap mealPic = ((BitmapDrawable)mealPicture.getDrawable()).getBitmap();
                Long currentTime = System.currentTimeMillis()/1000;
                String picName = currentTime.toString();

                //Store image
                String picturePath = saveToInternalStorage(mealPic, picName) + "/" +picName + ".png";
                //Store image

                MealContentProviderHelper helper = new MealContentProviderHelper(view.getContext());

                List<Ingredient> ingredients = new ArrayList<>();
                //Default ingredient
                ingredients.add(new Ingredient(0,
                        ingredientNameInput.getText().toString(),
                        Double.valueOf(ingredientQuantityInput.getText().toString()),
                        ingredientUnitSpinner.getSelectedItem().toString()));
                //Added Ingredients
                for(View row : listener.getIngredientRows()){
                    EditText ingredientName = row.findViewById(R.id.ingredientRowName);
                    EditText ingredientQuantity = row.findViewById(R.id.ingredientRowQuantity);
                    Spinner ingredientUnitSpinner = row.findViewById(R.id.ingredientRowUnitsSpinner);

                    String name = ingredientName.getText().toString();
                    double quantity = Double.parseDouble(ingredientQuantity.getText().toString());
                    String unit = ingredientUnitSpinner.getSelectedItem().toString();
                    ingredients.add(new Ingredient(0, name, quantity, unit));
                }

                helper.saveMeal(new Meal(mealNameInput.getText().toString(), picturePath, ingredients));
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        ingredientUnitSpinner.setSelection(0, true);
        View spinnerView = ingredientUnitSpinner.getSelectedView();
        ((TextView)spinnerView).setTextColor(getResources().getColor(R.color.gray));

        ingredientUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(ingredientUnitSpinner.getSelectedItem().toString().equals("no unit")) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.gray));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                    .placeholder(R.drawable.meal_default)
                    .error(R.drawable.meal_default)
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
