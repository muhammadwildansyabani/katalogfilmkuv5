package com.muhammadwildansyabani.katalogfilmkuv5.repository.provider;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.muhammadwildansyabani.katalogfilmkuv5.repository.db.LoadFilmAsync;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.db.LoadFilmCallback;

public class DataObserver extends ContentObserver {
    private final Context context;

    public DataObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        new LoadFilmAsync(context, (LoadFilmCallback) context).execute();

    }
}
