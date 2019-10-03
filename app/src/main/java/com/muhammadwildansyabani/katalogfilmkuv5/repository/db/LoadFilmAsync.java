package com.muhammadwildansyabani.katalogfilmkuv5.repository.db;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import static com.muhammadwildansyabani.katalogfilmkuv5.repository.db.DatabaseContract.CONTENT_URI;

public class LoadFilmAsync extends AsyncTask<Void,Void, Cursor> {
    private final WeakReference<Context> weakContext;
    private final WeakReference<LoadFilmCallback> weakCallback;

    public LoadFilmAsync(Context context, LoadFilmCallback callback) {
        weakContext = new WeakReference<>(context);
        weakCallback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        weakCallback.get().preExecute();
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        Context context = weakContext.get();
        return context.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    protected void onPostExecute(Cursor notes) {
        super.onPostExecute(notes);
        weakCallback.get().postExecute(notes);
    }
}
