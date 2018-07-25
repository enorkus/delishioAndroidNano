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

    public static final class IngredientEntry implements BaseColumns {
        public static final String TABLE_NAME = "ingredient";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_MEAL_ID = "mealId";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_UNIT = "unit";

        public static final Uri INGREDIENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildIngredientUriWithId(long id) {
            return INGREDIENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }
    }

    public static final class MealPlanEntry implements BaseColumns {
        public static final String TABLE_NAME = "mealPlan";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";

        public static final Uri MEAL_PLAN_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildMealPlanUriWithId(long id) {
            return MEAL_PLAN_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }
    }

    public static final class MealPlanMealRelationshipEntry implements BaseColumns {
        public static final String TABLE_NAME = "mealMealPlanRelationship";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_MEAL_ID = "mealId";
        public static final String COLUMN_MEAL_PLAN_ID = "mealPlanId";

        public static final Uri MEAL_MEAL_PLAN_RELATIONSHIP_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildMealPlanUriWithId(long id) {
            return MEAL_MEAL_PLAN_RELATIONSHIP_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }
    }

    public static final class ShoppingListEntry implements BaseColumns {
        public static final String TABLE_NAME = "shoppingList";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";

        public static final Uri SHOPPING_LIST_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildShoppingListUriWithId(long id) {
            return SHOPPING_LIST_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }
    }

    public static final class ShoppingListIngredientRelationshipEntry implements BaseColumns {
        public static final String TABLE_NAME = "shoppingListIngredientRelationship";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SHOPPING_LIST_ID = "shoppingListId";
        public static final String COLUMN_INGREDIENT_ID = "ingredientId";

        public static final Uri SHOPPING_LIST_INGREDIENT_RELATIONSHIP_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildShoppingListIngredientRelationshipUriWithId(long id) {
            return SHOPPING_LIST_INGREDIENT_RELATIONSHIP_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }
    }

}
