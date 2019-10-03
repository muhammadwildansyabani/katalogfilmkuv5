package com.muhammadwildansyabani.favoritefilmku;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

public class DataObserver extends ContentObserver {

    private final Context context;

    DataObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        new DataAsync(context, (MainActivity) context).execute();
    }
}
