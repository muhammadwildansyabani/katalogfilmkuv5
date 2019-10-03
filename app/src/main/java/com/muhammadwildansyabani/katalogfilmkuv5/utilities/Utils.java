package com.muhammadwildansyabani.katalogfilmkuv5.utilities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Genre;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.db.FilmImageRepository;
import com.muhammadwildansyabani.katalogfilmkuv5.widget.FavoriteFilmWidget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static int convertDpToPixel(float dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static String getObjectImageUrl(String url, String imageSize, String path) {
        return url + imageSize + path;
    }

    public static Bitmap getImageBitmap(Movies movie) {
        Bitmap getImage = null;
        for (Bitmap image : FilmImageRepository.imageList) {
            if (image.getGenerationId() == movie.getImageId()) {
                getImage = image;
                break;
            }
        }
        return getImage;
    }

    public static String formatRuntime(int runtime) {
        return String.format(Locale.getDefault(), "%dh %dm", runtime / 60, runtime % 60);
    }

    public static String getGenreList(ArrayList<Genre> genreArrayList){
        StringBuilder genreList = new StringBuilder();
        for(Genre genre : genreArrayList)
            genreList.append(genre.getName()).append(", ");
        return genreList.substring(0,genreList.length()-2);
    }

    public static String formatDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = dateFormat.parse(dateString);
            return newDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static void saveToInternalStorage(Context context, Bitmap bitmapImage, String filename) {
        ContextWrapper cw = new ContextWrapper(context);

        File directory = cw.getDir(Constant.LOCAL_IMAGE_FILE_PATH, Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory, filename);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap loadImageFromStorage(String path, String filename) {
        try {
            File file = new File(path, filename);
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateWidget(Context context){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(
                        (context),
                        FavoriteFilmWidget.class)
        );
        for(int widgetId : widgetIds)
            FavoriteFilmWidget.updateAppWidget(
                    context,
                    appWidgetManager,
                    widgetId
            );
    }
}
