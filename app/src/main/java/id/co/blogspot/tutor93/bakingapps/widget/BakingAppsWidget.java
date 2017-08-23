package id.co.blogspot.tutor93.bakingapps.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

import id.co.blogspot.tutor93.bakingapps.R;
import id.co.blogspot.tutor93.bakingapps.data.model.Ingredient;
import id.co.blogspot.tutor93.bakingapps.main.MainListActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, ArrayList<Ingredient> ingredientsList) {

        Intent intent = new Intent(context, MainListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_apps_widget);

        String list = null;
        for (Ingredient ingredient : ingredientsList) {
            list += ingredient.quantity.toString() + " "+
                    ingredient.measure + " " +
                    ingredient.ingredient + "\n";
        }
        views.setTextViewText(R.id.appwidget_text, list);

        views.setOnClickPendingIntent(R.id.widget_view, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    public static void setUpdateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds, ArrayList<Ingredient> ingredientsList) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredientsList);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

