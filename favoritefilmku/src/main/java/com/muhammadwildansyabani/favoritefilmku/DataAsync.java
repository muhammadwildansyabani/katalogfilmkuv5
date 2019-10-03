package com.muhammadwildansyabani.favoritefilmku;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import static com.muhammadwildansyabani.favoritefilmku.DatabaseContract.CONTENT_URI;

public class DataAsync extends AsyncTask<Void,Void, Cursor> {

    private final WeakReference<Context> weakContext;
    private final WeakReference<LoadFilmCallback> weakCallback;


    DataAsync(Context context, LoadFilmCallback callback) {
        weakContext = new WeakReference<>(context);
        weakCallback = new WeakReference<>(callback);
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        return weakContext.get()
                .getContentResolver()
                .query(CONTENT_URI, null, null, null, null);
    }

    @Override
    protected void onPostExecute(Cursor data) {
        super.onPostExecute(data);
        weakCallback.get().postExecute(data);
    }
}
