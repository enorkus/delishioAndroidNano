package com.enorkus.delishio.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.enorkus.delishio.data.DatabaseContract.*;

public class MealContentProvider extends ContentProvider {

    public static final int MEAL = 100;
    public static final int MEAL_BY_ID = 101;

    private DatabaseHelper helper;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.AUTHORITY;
        matcher.addURI(authority, MealEntry.TABLE_NAME, MEAL);
        matcher.addURI(authority, MealEntry.TABLE_NAME + "/#", MEAL_BY_ID);
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
                cursor = helper.getReadableDatabase().query(
                        MealEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
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
            case MEAL:
                long id = db.insert(MealEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    result = MealEntry.buildMealUriWithId(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
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
