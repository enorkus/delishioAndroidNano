package com.enorkus.delishio.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.enorkus.delishio.MainActivity;
import com.enorkus.delishio.R;

import com.enorkus.delishio.entity.Ingredient;

import java.util.List;

public class DelishioWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetIds, List<Ingredient> ingredients) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_shopping_list);
        String ingredientsString = "";
        for(Ingredient ingredient : ingredients) {
            ingredientsString = ingredientsString + ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getName() + "\n";
        }
        views.setTextViewText(R.id.widgetTV, ingredientsString);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widgetWrapper, pendingIntent);

        for(int widgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
