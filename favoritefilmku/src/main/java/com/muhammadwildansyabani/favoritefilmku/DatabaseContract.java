package com.muhammadwildansyabani.favoritefilmku;

import android.database.Cursor;
import android.net.Uri;

import static com.muhammadwildansyabani.favoritefilmku.Constant.TABLE_NAME;

class DatabaseContract {

    private static final String SCHEME = "content";
    private static final String AUTHORITY = "com.muhammadwildansyabani.katalogfilmkuv5";

    static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    static int getColumnInt(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex(Constant.COLUMN_SCORE));
    }

    static long getColumnLong(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndex(Constant.COLUMN_ID));
    }
}
