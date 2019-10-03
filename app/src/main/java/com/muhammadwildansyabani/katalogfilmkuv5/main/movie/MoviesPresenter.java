package com.muhammadwildansyabani.katalogfilmkuv5.main.movie;

import android.content.Context;
import android.content.Intent;

import com.muhammadwildansyabani.katalogfilmkuv5.detail.DetailActivity;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.MoviesViewModel;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;

public class MoviesPresenter implements MoviesContract.Presenter {
    private final Context context;
    private final MoviesContract.View mView;
    private MoviesViewModel moviesViewModel;

    MoviesPresenter(MoviesContract.View mainView, Context context){
        this.context = context;
        this.mView = mainView;
        this.mView.setPresenter(this);
    }

    @Override
    public void prepareData(MoviesViewModel moviesViewModel) {
        this.moviesViewModel = moviesViewModel;
        String url = Constant.getUrlOf(
                Constant.URL_TYPE_DISCOVER,
                Constant.URL_MOVIES,
                "",
                ((MoviesFragment)mView).getContext());
        this.moviesViewModel.setMovie(mView, url);
    }

    @Override
    public void navigateView(Movies movieData) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movieData);
        context.startActivity(intent);
    }

    @Override
    public boolean onAllDataFinishLoaded() {
        return moviesViewModel.getLoadedItemCounter() >= moviesViewModel.getTotalItem();
    }
}
