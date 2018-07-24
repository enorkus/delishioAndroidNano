package com.enorkus.delishio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.enorkus.delishio.data.DatabaseContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "delishio.db";
    private static final int DATABASE_VERSION = 17;
    private static final String CREATE_MEALS_TABLE = "CREATE TABLE "
            + MealEntry.TABLE_NAME
            + " (" + MealEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MealEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + MealEntry.COLUMN_PICTURE_PATH + " TEXT NOT NULL" + ");";

    private static final String CREATE_INGREDIENTS_TABLE = "CREATE TABLE "
            + IngredientEntry.TABLE_NAME + " ("
            + IngredientEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + IngredientEntry.COLUMN_MEAL_ID + " INTEGER NOT NULL, "
            + IngredientEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + IngredientEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
            + IngredientEntry.COLUMN_UNIT + " TEXT NOT NULL"
            + ");";

    private static final String CREATE_MEAL_PLANS_TABLE = "CREATE TABLE "
            + MealPlanEntry.TABLE_NAME + " ("
            + MealPlanEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + MealPlanEntry.COLUMN_NAME + " TEXT NOT NULL"
            + ");";

    private static final String CREATE_MEAL_MEAL_PLAN_RELATIONSHIP_TABLE = "CREATE TABLE "
            + MealMealPlanRelationshipEntry.TABLE_NAME + " ("
            + MealMealPlanRelationshipEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + MealMealPlanRelationshipEntry.COLUMN_MEAL_ID + " INTEGER NOT NULL, "
            + MealMealPlanRelationshipEntry.COLUMN_MEAL_PLAN_ID + " INTEGER NOT NULL"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MEALS_TABLE);
        sqLiteDatabase.execSQL(CREATE_INGREDIENTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_MEAL_PLANS_TABLE);
        sqLiteDatabase.execSQL(CREATE_MEAL_MEAL_PLAN_RELATIONSHIP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MealEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IngredientEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MealPlanEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MealMealPlanRelationshipEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
