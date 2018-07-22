package com.enorkus.delishio.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.enorkus.delishio";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class MealEntry implements BaseColumns {
        public static final String TABLE_NAME = "meal";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PICTURE_PATH = "picturePath";

        public static final Uri MEAL_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildMealUriWithId(long id) {
            return MEAL_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }
    }

//    public static final class IngredientEntry implements BaseColumns {
//        public static final String TABLE_NAME = "ingredient";
//        public static final String COLUMN_ID = "id";
//        public static final String COLUMN_MEAL_ID = "mealId";
//        public static final String COLUMN_NAME = "name";
//        public static final String COLUMN_QUANTITY = "quantity";
//        public static final String COLUMN_UNIT = "unit";
//    }

}