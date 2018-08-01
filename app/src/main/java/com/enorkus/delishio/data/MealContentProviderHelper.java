package com.enorkus.delishio.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.enorkus.delishio.entity.Ingredient;
import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.entity.MealPlan;
import com.enorkus.delishio.entity.ShoppingList;

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
            if(ingredient.getUnit().equals("no unit")) {
                ingredientValues.put(IngredientEntry.COLUMN_UNIT, "");
            } else {
                ingredientValues.put(IngredientEntry.COLUMN_UNIT, ingredient.getUnit());
            }
            ctx.getContentResolver().insert(IngredientEntry.INGREDIENT_URI, ingredientValues);
        }
        return uri;
    }

    public List<Meal> fetchAllMeals() {
        Cursor cursor =  ctx.getContentResolver().query(MealEntry.MEAL_URI, null, null, null, null, null);
        List<Meal> meals = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        Meal currentMeal = new Meal(0, "", "");

        int currentMealId = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int mealId = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_ID));
            //avoid duplicates since query is joined with ingredients table
            if(currentMealId != mealId) {
                currentMealId = mealId;
                ingredients = new ArrayList<>();
                String mealName = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_NAME));
                String picturePath = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_PICTURE_PATH));
                currentMeal = new Meal(mealId, mealName, picturePath);
                meals.add(currentMeal);
            }
            int ingredientId = cursor.getInt(cursor.getColumnIndex("ingredientId"));
            String ingredientName = cursor.getString(cursor.getColumnIndex("ingredientName"));
            String ingredientUnit = cursor.getString(cursor.getColumnIndex(IngredientEntry.COLUMN_UNIT));
            double ingredientQuantity = cursor.getDouble(cursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY));
            ingredients.add(new Ingredient(ingredientId, ingredientName, ingredientQuantity, ingredientUnit));
            currentMeal.setIngredients(ingredients);
        }
        cursor.close();
        return meals;
    }

    private List<Ingredient> fetchIngredientsByMealId(int mealId) {
        Cursor ingredientsCursor = ctx.getContentResolver().query(IngredientEntry.buildIngredientUriWithId(mealId), null, null, null, null, null);
        List<Ingredient> ingredients = new ArrayList<>();
        for (ingredientsCursor.moveToFirst(); !ingredientsCursor.isAfterLast(); ingredientsCursor.moveToNext()) {
            int ingredientId = ingredientsCursor.getInt(ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_ID));
            String ingredientName = ingredientsCursor.getString(ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_NAME));
            double ingredientQuantity = ingredientsCursor.getDouble(ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY));
            String ingredientUnit = ingredientsCursor.getString(ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_UNIT));
            Ingredient ingredient = new Ingredient(ingredientId, mealId, ingredientName, ingredientQuantity, ingredientUnit);
            ingredients.add(ingredient);
        }
        ingredientsCursor.close();
        return ingredients;
    }

    public List<MealPlan> fetchAllMealPlans() {
        Cursor cursor =  ctx.getContentResolver().query(MealPlanEntry.MEAL_PLAN_URI, null, null, null, null, null);
        List<MealPlan> mealPlans = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int mealPlanId = cursor.getInt(cursor.getColumnIndex(MealPlanEntry.COLUMN_ID));
            String mealPlanName = cursor.getString(cursor.getColumnIndex(MealPlanEntry.COLUMN_NAME));
            Cursor mealMealPlanCursor = ctx.getContentResolver().query(MealPlanMealRelationshipEntry.buildMealPlanUriWithId(mealPlanId), null, null, null, null, null);

            List<Meal> meals = new ArrayList<>();
            for (mealMealPlanCursor.moveToFirst(); !mealMealPlanCursor.isAfterLast(); mealMealPlanCursor.moveToNext()) {
                int mealId = mealMealPlanCursor.getInt(mealMealPlanCursor.getColumnIndex(MealPlanMealRelationshipEntry.COLUMN_MEAL_ID));
                Cursor mealsCursor = ctx.getContentResolver().query(MealEntry.buildMealUriWithId(mealId), null, null, null, null, null);
                for (mealsCursor.moveToFirst(); !mealsCursor.isAfterLast(); mealsCursor.moveToNext()) {
                    String mealName = mealsCursor.getString(mealsCursor.getColumnIndex(MealEntry.COLUMN_NAME));
                    String picturePath = mealsCursor.getString(mealsCursor.getColumnIndex(MealEntry.COLUMN_PICTURE_PATH));

                    List<Ingredient> ingredients = fetchIngredientsByMealId(mealId);
                    Meal meal = new Meal(mealName, picturePath, ingredients);
                    meals.add(meal);
                }
                mealsCursor.close();
                mealMealPlanCursor.close();
            }

            MealPlan mealPlan = new MealPlan(mealPlanName, meals);
            mealPlans.add(mealPlan);
        }
        cursor.close();
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
            values.put(MealPlanMealRelationshipEntry.COLUMN_MEAL_PLAN_ID, mealPlanId);
            values.put(MealPlanMealRelationshipEntry.COLUMN_MEAL_ID, meal.getId());
            ctx.getContentResolver().insert(MealPlanMealRelationshipEntry.MEAL_MEAL_PLAN_RELATIONSHIP_URI, values);
        }
    }

    public List<ShoppingList> fetchAllShoppingLists() {
        Cursor cursor = ctx.getContentResolver().query(ShoppingListEntry.SHOPPING_LIST_URI, null, null, null, null, null);
        List<ShoppingList> shoppingLists = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ShoppingList shoppingList = null;
            List<Ingredient> ingredients = new ArrayList<>();
            int shoppingListId = cursor.getInt(cursor.getColumnIndex(ShoppingListEntry.COLUMN_ID));
            String shoppingListName = cursor.getString(cursor.getColumnIndex(ShoppingListEntry.COLUMN_NAME));

            Cursor relationShipCursor = ctx.getContentResolver().query(ShoppingListIngredientRelationshipEntry.buildShoppingListIngredientRelationshipUriWithId(shoppingListId), null, null, null, null, null);
            for (relationShipCursor.moveToFirst(); !relationShipCursor.isAfterLast(); relationShipCursor.moveToNext()) {
                int ingredientId = relationShipCursor.getInt(relationShipCursor.getColumnIndex(ShoppingListIngredientRelationshipEntry.COLUMN_INGREDIENT_ID));

                Cursor ingredientCursor = ctx.getContentResolver().query(IngredientEntry.buildIngredientUriWithId(ingredientId), null, null, null, null, null);
                for (ingredientCursor.moveToFirst(); !ingredientCursor.isAfterLast(); ingredientCursor.moveToNext()) {
                    String ingredientName = ingredientCursor.getString(ingredientCursor.getColumnIndex(IngredientEntry.COLUMN_NAME));
                    double ingredientQuantity = ingredientCursor.getDouble(ingredientCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY));
                    String ingredientUnit = ingredientCursor.getString(ingredientCursor.getColumnIndex(IngredientEntry.COLUMN_UNIT));
                    Ingredient ingredient = new Ingredient(0, ingredientName, ingredientQuantity, ingredientUnit);
                    ingredients.add(ingredient);
                }
                ingredientCursor.close();
                relationShipCursor.close();
            }

            shoppingList = new ShoppingList(shoppingListName, ingredients);
            shoppingLists.add(shoppingList);
        }
        cursor.close();
        return shoppingLists;
    }

    public void saveShoppingList(ShoppingList shoppingList) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListEntry.COLUMN_NAME, shoppingList.getName());
        Uri uri = ctx.getContentResolver().insert(ShoppingListEntry.SHOPPING_LIST_URI, values);
        String shoppingListId = uri.getLastPathSegment();

        for(Ingredient ingredient : shoppingList.getIngredients()) {
            ContentValues shoppingListIngredientContentValues = new ContentValues();
            shoppingListIngredientContentValues.put(ShoppingListIngredientRelationshipEntry.COLUMN_SHOPPING_LIST_ID, shoppingListId);
            shoppingListIngredientContentValues.put(ShoppingListIngredientRelationshipEntry.COLUMN_INGREDIENT_ID, ingredient.getId());
            ctx.getContentResolver().insert(ShoppingListIngredientRelationshipEntry.SHOPPING_LIST_INGREDIENT_RELATIONSHIP_URI, shoppingListIngredientContentValues);
        }

    }
}
