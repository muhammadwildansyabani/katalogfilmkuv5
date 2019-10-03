package com.muhammadwildansyabani.favoritefilmku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.ArrayList;

import static com.muhammadwildansyabani.favoritefilmku.Constant.THREAD_NAME;
import static com.muhammadwildansyabani.favoritefilmku.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoadFilmCallback {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        HandlerThread handlerThread = new HandlerThread(THREAD_NAME);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        new DataAsync(this, this).execute();
    }

    @Override
    public void postExecute(Cursor filmCursor) {
        ArrayList<MovieParcelable> movieParcelableArrayList = new ArrayList<>();
        while(filmCursor.moveToNext()){
            MovieParcelable movieParcelable = new MovieParcelable(filmCursor);
            Log.i("DRG","filmCursor : " + movieParcelable.getTitle());
            movieParcelableArrayList.add(movieParcelable);
        }
        MovieAdapter adapter = new MovieAdapter();
        adapter.setFavoriteMovieData(movieParcelableArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
