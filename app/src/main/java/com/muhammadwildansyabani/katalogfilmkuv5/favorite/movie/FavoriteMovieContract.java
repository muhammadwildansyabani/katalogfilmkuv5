package com.muhammadwildansyabani.katalogfilmkuv5.favorite.movie;

import com.muhammadwildansyabani.katalogfilmkuv5.BasePresenter;
import com.muhammadwildansyabani.katalogfilmkuv5.BaseView;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;

import java.util.ArrayList;

public interface FavoriteMovieContract {
    interface View extends BaseView<Presenter> {
        void initView();
        void showFavoriteData(ArrayList<Movies> moviesArrayList);
        void showAlertDialog(Movies movie);
    }

    interface Presenter extends BasePresenter {
        void prepareData(FavoriteMovieViewModel favoriteMovieViewModel);
        void navigateView(Movies movieData);
    }
}
