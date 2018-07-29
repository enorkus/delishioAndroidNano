package com.enorkus.delishio.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.enorkus.delishio.entity.Meal;
import com.enorkus.delishio.entity.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import static com.enorkus.delishio.data.DatabaseContract.*;

public class MealContentProvider extends ContentProvider {

    public static final int MEAL = 100;
    public static final int MEAL_BY_ID = 101;
    public static final int INGREDIENT = 200;
    public static final int INGREDIENT_BY_MEAL_ID = 201;
    public static final int MEAL_PLAN = 300;
    public static final int MEAL_PLAN_BY_ID = 301;
    public static final int MEAL_MEAL_PLAN_RELATIONSHIP = 400;
    public static final int MEAL_IDS_BY_MEAL_PLAN_ID = 401;
    public static final int SHOPPING_LIST = 500;
    public static final int SHOPPING_LIST_INGREDIENT_RELATIONSHIP = 502;
    public static final int INGREDIENT_IDS_BY_SHOPPING_LIST_ID = 503;

    private DatabaseHelper helper;
    private MealContentProviderHelper contentHelper = new MealContentProviderHelper(getContext());

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.AUTHORITY;
        matcher.addURI(authority, MealEntry.TABLE_NAME, MEAL);
        matcher.addURI(authority, MealEntry.TABLE_NAME + "/#", MEAL_BY_ID);
        matcher.addURI(authority, IngredientEntry.TABLE_NAME, INGREDIENT);
        matcher.addURI(authority, IngredientEntry.TABLE_NAME + "/#", INGREDIENT_BY_MEAL_ID);
        matcher.addURI(authority, MealPlanEntry.TABLE_NAME, MEAL_PLAN);
        matcher.addURI(authority, MealPlanEntry.TABLE_NAME + "/#", MEAL_PLAN_BY_ID);
        matcher.addURI(authority, MealPlanMealRelationshipEntry.TABLE_NAME, MEAL_MEAL_PLAN_RELATIONSHIP);
        matcher.addURI(authority, MealPlanMealRelationshipEntry.TABLE_NAME + "/#", MEAL_IDS_BY_MEAL_PLAN_ID);
        matcher.addURI(authority, ShoppingListEntry.TABLE_NAME, SHOPPING_LIST);
        matcher.addURI(authority, ShoppingListIngredientRelationshipEntry.TABLE_NAME, SHOPPING_LIST_INGREDIENT_RELATIONSHIP);
        matcher.addURI(authority, ShoppingListIngredientRelationshipEntry.TABLE_NAME + "/#", INGREDIENT_IDS_BY_SHOPPING_LIST_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MEAL_BY_ID: {
                String id = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{id};
                cursor = helper.getReadableDatabase().query(
                        MealEntry.TABLE_NAME,
                        projection,
                        MealEntry.COLUMN_ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MEAL: {
                cursor = helper.getReadableDatabase().rawQuery(
                        "SELECT meal.id, meal.name, meal.picturePath, ingredient.id as ingredientId, ingredient.name as ingredientName, ingredient.quantity, ingredient.unit " +
                                "FROM meal " +
                                "LEFT JOIN ingredient on meal.id = ingredient.mealId " +
                                "order by meal.id",
                        null);
                break;
            }
            case INGREDIENT_BY_MEAL_ID: {
                String mealId = uri.getLastPathSegment();
                cursor = helper.getReadableDatabase().query(
                        IngredientEntry.TABLE_NAME,
                        projection,
                        "mealId=?",
                        new String[]{mealId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MEAL_PLAN: {
                cursor = helper.getReadableDatabase().query(
                        MealPlanEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MEAL_IDS_BY_MEAL_PLAN_ID: {
                String mealPlanId = uri.getLastPathSegment();
                cursor = helper.getReadableDatabase().query(
                        MealPlanMealRelationshipEntry.TABLE_NAME,
                        projection,
                        "mealPlanId=?",
                        new String[]{mealPlanId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case SHOPPING_LIST: {
                cursor = helper.getReadableDatabase().query(
                        ShoppingListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case INGREDIENT_IDS_BY_SHOPPING_LIST_ID: {
                String mealPlanId = uri.getLastPathSegment();
                cursor = helper.getReadableDatabase().query(
                        ShoppingListIngredientRelationshipEntry.TABLE_NAME,
                        projection,
                        "shoppingListId=?",
                        new String[]{mealPlanId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = helper.getWritableDatabase();

        Uri result;
        switch (uriMatcher.match(uri)) {
            case MEAL: {
                long id = db.insert(MealEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    result = MealEntry.buildMealUriWithId(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case INGREDIENT: {
                long id = db.insert(IngredientEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    result = IngredientEntry.buildIngredientUriWithId(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MEAL_PLAN: {
                long id = db.insert(MealPlanEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    result = MealPlanEntry.buildMealPlanUriWithId(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MEAL_MEAL_PLAN_RELATIONSHIP: {
                long id = db.insert(MealPlanMealRelationshipEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    result = MealPlanMealRelationshipEntry.buildMealPlanUriWithId(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case SHOPPING_LIST: {
                long id = db.insert(ShoppingListEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    result = ShoppingListEntry.buildShoppingListUriWithId(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case SHOPPING_LIST_INGREDIENT_RELATIONSHIP: {
                long id = db.insert(ShoppingListIngredientRelationshipEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    result = ShoppingListIngredientRelationshipEntry.buildShoppingListIngredientRelationshipUriWithId(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
