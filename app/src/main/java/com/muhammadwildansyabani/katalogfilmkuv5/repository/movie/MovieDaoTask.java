package com.muhammadwildansyabani.katalogfilmkuv5.repository.movie;

import android.os.AsyncTask;

import com.muhammadwildansyabani.katalogfilmkuv5.MainActivity;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieDaoTask extends AsyncTask<String,Void, List<MovieEntity>> {
    private long id;
    private List<MovieEntity> movieEntities = new ArrayList<>();

    public void setId(long id ){
        this.id = id;
    }

    public void setMovieEntities(MovieEntity... movieEntities) {
        this.movieEntities.clear();
        Collections.addAll(this.movieEntities, movieEntities);
    }



    @Override
    protected List<MovieEntity> doInBackground(String... strings) {
        for(String param : strings){
            switch (param) {
                case Constant.GET_FAVORITE_MOVIES:
                    movieEntities = MainActivity.getDatabase().movieDao().getAll();
                    break;
                case Constant.INSERT_ALL_MOVIES:
                    for (MovieEntity movieEntity : movieEntities)
                        MainActivity.getDatabase().movieDao().insertAll(movieEntity);
                    break;
                case Constant.DELETE_MOVIE:
                    MainActivity.getDatabase().movieDao().delete(movieEntities.get(0));
                    break;
                case Constant.FIND_MOVIE:
                    try {
                        movieEntities.clear();
                        movieEntities.add(MainActivity.getDatabase().movieDao().find(id));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return movieEntities;
    }
}
