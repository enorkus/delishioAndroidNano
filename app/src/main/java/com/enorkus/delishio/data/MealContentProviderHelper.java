package com.enorkus.delishio.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.enorkus.delishio.entity.Ingredient;
import com.enorkus.delishio.entity.Meal;

import java.util.ArrayList;
import java.util.List;

import static com.enorkus.delishio.data.DatabaseContract.*;

public class MealContentProviderHelper {

    private Context ctx;

    public MealContentProviderHelper(Context ctx) {
        this.ctx = ctx;
    }

    public Uri saveMeal(Meal meal) {
        ContentValues values = new ContentValues();
        values.put(MealEntry.COLUMN_NAME, meal.getName());
        values.put(MealEntry.COLUMN_PICTURE_PATH, meal.getPicturePath());
        //save meal
        Uri uri = ctx.getContentResolver().insert(MealEntry.MEAL_URI, values);
        //save ingredients
        String mealId = uri.getLastPathSegment();
        for(Ingredient ingredient : meal.getIngredients()) {
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put(IngredientEntry.COLUMN_MEAL_ID, mealId);
            ingredientValues.put(IngredientEntry.COLUMN_NAME, ingredient.getName());
            ingredientValues.put(IngredientEntry.COLUMN_QUANTITY, ingredient.getQuantity());
            ingredientValues.put(IngredientEntry.COLUMN_UNIT, ingredient.getUnit());
            ctx.getContentResolver().insert(IngredientEntry.INGREDIENT_URI, ingredientValues);
        }
        return uri;
    }

    public List<Meal> fetchAllMeals() {
        Cursor cursor =  ctx.getContentResolver().query(MealEntry.MEAL_URI, null, null, null, null, null);
        List<Meal> meals = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.MealEntry.COLUMN_NAME));
            String picturePath = cursor.getString(cursor.getColumnIndex(DatabaseContract.MealEntry.COLUMN_PICTURE_PATH));
            meals.add(new Meal(name, picturePath, null));
        }
        return meals;
    }

}
