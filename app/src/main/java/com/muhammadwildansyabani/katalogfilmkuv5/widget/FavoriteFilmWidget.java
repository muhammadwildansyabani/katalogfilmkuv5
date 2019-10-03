package com.muhammadwildansyabani.katalogfilmkuv5.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import androidx.room.Room;

import com.muhammadwildansyabani.katalogfilmkuv5.R;
import com.muhammadwildansyabani.katalogfilmkuv5.detail.DetailActivity;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.AppDatabase;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.movie.MovieEntity;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteFilmWidget extends AppWidgetProvider {
    private static final String TOAST_ACTION = "TOAST_ACTION";
    public static final String EXTRA_ITEM = "item";
    private AppDatabase database;


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_film_widget);
        views.setRemoteAdapter(R.id.film_stack_view, intent);

        Intent toastIntent = new Intent(context, FavoriteFilmWidget.class);
        toastIntent.setAction(FavoriteFilmWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.film_stack_view, toastPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.film_stack_view);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.film_stack_view);
        }
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                final int viewImageId = intent.getIntExtra(EXTRA_ITEM, 0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        database = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "movies").build();
                        MovieEntity movieEntity = database.movieDao().find(viewImageId);
                        Movies movie = new Movies();
                        movie.setId(movieEntity.getId());
                        movie.setTitle(movieEntity.getTitle());
                        movie.setDesc(movieEntity.getDesc());
                        movie.setScore(movieEntity.getScore());
                        Intent activityIntent = new Intent(context, DetailActivity.class);
                        activityIntent.putExtra(DetailActivity.EXTRA_MOVIE,movie);
                        context.startActivity(activityIntent);
                    }
                }).start();

            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

