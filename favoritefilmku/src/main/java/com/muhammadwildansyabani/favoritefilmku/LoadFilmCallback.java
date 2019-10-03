package com.muhammadwildansyabani.favoritefilmku;

import android.database.Cursor;

public interface LoadFilmCallback {
    void postExecute(Cursor filmCursor);
}
