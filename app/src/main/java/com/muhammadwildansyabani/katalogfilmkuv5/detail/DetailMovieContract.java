package com.muhammadwildansyabani.katalogfilmkuv5.detail;

import android.content.Intent;

import com.muhammadwildansyabani.katalogfilmkuv5.BasePresenter;
import com.muhammadwildansyabani.katalogfilmkuv5.BaseView;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;

public interface DetailMovieContract {
    interface View extends BaseView<Presenter> {
        void initView();
        void showMovieDetailInfo();
        void showFilmDetailsData(Object filmData);
        void showLoading(boolean state);
        void showToastMessage(String message);
        void updateButtonImageState(boolean flag);
    }

    interface Presenter extends BasePresenter {
        void prepareFilmDetails(final Movies movie, String category);
        Movies retrieveIntentMovieData(Intent intent);
        Tv retrieveIntentTvData(Intent intent);
        void addToTvFavorite(Tv tv, Movies movieData);
        void addToMovieFavorite(Movies movie, Movies movieData);
        void removeFromTvFavorite(Tv tv, Movies movieData);
        void removeFromMovieFavorite(Movies movie, Movies movieData);
    }
}
