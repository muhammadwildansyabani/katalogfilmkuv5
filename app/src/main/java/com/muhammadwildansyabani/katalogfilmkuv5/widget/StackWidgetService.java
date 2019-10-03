package com.muhammadwildansyabani.katalogfilmkuv5.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteStackRemoteViewFactory(this.getApplicationContext());
    }
}
