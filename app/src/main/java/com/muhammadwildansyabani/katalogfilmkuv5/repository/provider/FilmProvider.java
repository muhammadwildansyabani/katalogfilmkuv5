package com.muhammadwildansyabani.katalogfilmkuv5.repository.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.muhammadwildansyabani.katalogfilmkuv5.repository.AppDatabase;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.movie.MovieEntity;

import java.util.Objects;

import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.TABLE_NAME;

public class FilmProvider extends ContentProvider {
    public static final String AUTHORITY = "com.muhammadwildansyabani.katalogfilmkuv5";
    public static final Uri URI_MENU = Uri.parse(
            "content://" + AUTHORITY + "/" + TABLE_NAME);
    private static final int DIR = 1;
    private static final int DIR_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private AppDatabase database;

    static {

        sUriMatcher.addURI(AUTHORITY, "movies", DIR);

        sUriMatcher.addURI(AUTHORITY, "movies" + "/#", DIR_ID);
    }

    @Override
    public boolean onCreate() {
        database = Room.databaseBuilder(Objects.requireNonNull(getContext()),
                AppDatabase.class, "movies").build();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        final int code = sUriMatcher.match(uri);
        if (code == DIR || code == DIR_ID) {
            switch (sUriMatcher.match(uri)) {
                case DIR:
                    cursor = database.movieDao().selectAll();
                    break;
                case DIR_ID:
                    cursor = database.movieDao().findById(ContentUris.parseId(uri));
                    break;
                default:
                    cursor = null;
                    break;
            }
            return cursor;
        }else
            throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + "movies";
            case DIR_ID:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + "movies";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        switch (sUriMatcher.match(uri)) {
            case DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = database.movieDao().insertAll(new MovieEntity(contentValues));

                context.getContentResolver().notifyChange(uri, new DataObserver(new Handler(),getContext()));
                return ContentUris.withAppendedId(uri, id);
            case DIR_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case DIR_ID:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final MovieEntity movieEntity = new MovieEntity(values);
                movieEntity.setId(ContentUris.parseId(uri));
                final int count = database.movieDao().update(movieEntity);
                context.getContentResolver().notifyChange(uri, new DataObserver(new Handler(),getContext()));
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case DIR_ID:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = database.movieDao().deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, new DataObserver(new Handler(),getContext()));
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
