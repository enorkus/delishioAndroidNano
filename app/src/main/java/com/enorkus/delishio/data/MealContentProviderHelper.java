package com.enorkus.delishio.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.enorkus.delishio.entity.Ingredient;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.entity.MealPlan;

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
            int mealId = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_NAME));
            String picturePath = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_PICTURE_PATH));

            //Fetch ingredients
            List<Ingredient> ingredients = fetchIngredientsByMealId(mealId);
            meals.add(new Meal(mealId, name, picturePath, ingredients));
        }
        return meals;
    }

    private List<Ingredient> fetchIngredientsByMealId(int mealId) {
        Cursor ingredientsCursor = ctx.getContentResolver().query(IngredientEntry.buildIngredientUriWithId(mealId), null, null, null, null, null);
        List<Ingredient> ingredients = new ArrayList<>();
        for (ingredientsCursor.moveToFirst(); !ingredientsCursor.isAfterLast(); ingredientsCursor.moveToNext()) {
            String ingredientName = ingredientsCursor.getString(ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_NAME));
            double ingredientQuantity = ingredientsCursor.getDouble(ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY));
            String ingredientUnit = ingredientsCursor.getString(ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_UNIT));
            Ingredient ingredient = new Ingredient(mealId, ingredientName, ingredientQuantity, ingredientUnit);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public List<MealPlan> fetchAllMealPlans() {
        Cursor cursor =  ctx.getContentResolver().query(MealPlanEntry.MEAL_PLAN_URI, null, null, null, null, null);
        List<MealPlan> mealPlans = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int mealPlanId = cursor.getInt(cursor.getColumnIndex(MealPlanEntry.COLUMN_ID));
            String mealPlanName = cursor.getString(cursor.getColumnIndex(MealPlanEntry.COLUMN_NAME));
            Cursor mealMealPlanCursor = ctx.getContentResolver().query(MealMealPlanRelationshipEntry.buildMealPlanUriWithId(mealPlanId), null, null, null, null, null);

            List<Meal> meals = new ArrayList<>();
            for (mealMealPlanCursor.moveToFirst(); !mealMealPlanCursor.isAfterLast(); mealMealPlanCursor.moveToNext()) {
                int mealId = mealMealPlanCursor.getInt(mealMealPlanCursor.getColumnIndex(MealMealPlanRelationshipEntry.COLUMN_MEAL_ID));
                Cursor mealsCursor = ctx.getContentResolver().query(MealEntry.buildMealUriWithId(mealId), null, null, null, null, null);
                for (mealsCursor.moveToFirst(); !mealsCursor.isAfterLast(); mealsCursor.moveToNext()) {
                    String mealName = mealsCursor.getString(mealsCursor.getColumnIndex(MealEntry.COLUMN_NAME));
                    String picturePath = mealsCursor.getString(mealsCursor.getColumnIndex(MealEntry.COLUMN_PICTURE_PATH));

                    List<Ingredient> ingredients = fetchIngredientsByMealId(mealId);
                    Meal meal = new Meal(mealName, picturePath, ingredients);
                    meals.add(meal);
                }
            }

            MealPlan mealPlan = new MealPlan(mealPlanName, meals);
            mealPlans.add(mealPlan);
        }
        return mealPlans;
    }

    public Uri saveMealPlan(MealPlan mealPlan) {
        ContentValues values = new ContentValues();
        values.put(MealPlanEntry.COLUMN_NAME, mealPlan.getName());
        Uri uri = ctx.getContentResolver().insert(MealPlanEntry.MEAL_PLAN_URI, values);
        String mealId = uri.getLastPathSegment();
        saveMealMealPlanRelationship(Integer.valueOf(mealId), mealPlan.getMeals());
        return uri;
    }

    public void saveMealMealPlanRelationship(int mealPlanId, List<Meal> meals) {
        for(Meal meal : meals) {
            ContentValues values = new ContentValues();
            values.put(MealMealPlanRelationshipEntry.COLUMN_MEAL_PLAN_ID, mealPlanId);
            values.put(MealMealPlanRelationshipEntry.COLUMN_MEAL_ID, meal.getId());
            ctx.getContentResolver().insert(MealMealPlanRelationshipEntry.MEAL_MEAL_PLAN_RELATIONSHIP_URI, values);
        }
    }
}
