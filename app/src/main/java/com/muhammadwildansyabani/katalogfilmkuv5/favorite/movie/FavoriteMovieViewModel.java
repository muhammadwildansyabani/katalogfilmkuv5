package com.muhammadwildansyabani.katalogfilmkuv5.favorite.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.movie.MovieDaoTask;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.movie.MovieEntity;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Movies>> movieList = new MutableLiveData<>();


    public void setMovie() {
        MovieDaoTask task = new MovieDaoTask();
        try{
            movieList.setValue(getFavoriteData(task.execute(Constant.GET_FAVORITE_MOVIES).get()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<Movies> getFavoriteData(List<MovieEntity> movieEntities) {
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
        for(MovieEntity movieEntity : movieEntities){
            Movies movie = new Movies();
            movie.setId(movieEntity.getId());
            movie.setTitle(movieEntity.getTitle());
            movie.setDesc(movieEntity.getDesc());
            movie.setReleaseDate(movieEntity.getReleaseDate());
            movie.setRuntime(movieEntity.getRuntime());
            movie.setScore(movieEntity.getScore());
            moviesArrayList.add(movie);
        }
        return moviesArrayList;
    }
    public LiveData<ArrayList<Movies>> getMovieList(){
        return movieList;
    }
}
