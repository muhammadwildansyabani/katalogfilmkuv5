package com.muhammadwildansyabani.katalogfilmkuv5.main.movie;

import com.muhammadwildansyabani.katalogfilmkuv5.BasePresenter;
import com.muhammadwildansyabani.katalogfilmkuv5.BaseView;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.MoviesViewModel;

import java.util.ArrayList;

public interface MoviesContract {
    interface View extends BaseView<Presenter> {
        void showMovieList(ArrayList<Movies> moviesArrayList);
        void showReloadButton(boolean state);
    }

    interface Presenter extends BasePresenter {
        void prepareData(MoviesViewModel moviesViewModel);
        void navigateView(Movies movieData);
        boolean onAllDataFinishLoaded();
    }
}
