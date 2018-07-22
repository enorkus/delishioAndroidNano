package com.enorkus.delishio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.enorkus.delishio.data.DatabaseContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "delishio.db";
    private static final int DATABASE_VERSION = 11;
    private static final String CREATE_MEALS_TABLE = "CREATE TABLE "
            + MealEntry.TABLE_NAME
            + " (" + MealEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MealEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + MealEntry.COLUMN_PICTURE_PATH + " TEXT NOT NULL" + ");";

//    private static final String CREATE_INGREDIENTS_TABLE = "CREATE TABLE "
//            + IngredientEntry.TABLE_NAME + " ("
//            + IngredientEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
//            + IngredientEntry.COLUMN_MEAL_ID + " INTEGER NOT NULL, "
//            + IngredientEntry.COLUMN_NAME + " TEXT NOT NULL, "
//            + IngredientEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
//            + IngredientEntry.COLUMN_UNIT + " TEXT NOT NULL, "
//            + "FOREIGN KEY(" + IngredientEntry.COLUMN_MEAL_ID + ") " + "REFERENCES " + MealEntry.TABLE_NAME + "(" + MealEntry.COLUMN_ID + ")"
//            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MEALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MealEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
