package com.enorkus.delishio.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.enorkus.delishio.entity.Meal;

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
        return ctx.getContentResolver().insert(MealEntry.MEAL_URI, values);
    }

    public Cursor fetchAllMeals() {
        return ctx.getContentResolver().query(MealEntry.MEAL_URI, null, null, null, null, null);
    }

}
