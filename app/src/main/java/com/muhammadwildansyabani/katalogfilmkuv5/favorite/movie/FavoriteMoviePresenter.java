package com.muhammadwildansyabani.katalogfilmkuv5.favorite.movie;

import android.content.Context;
import android.content.Intent;

import com.muhammadwildansyabani.katalogfilmkuv5.detail.DetailActivity;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;

public class FavoriteMoviePresenter implements FavoriteMovieContract.Presenter {
    private final Context context;
    private final FavoriteMovieContract.View mView;

    FavoriteMoviePresenter(FavoriteMovieContract.View mView, Context context){
        this.context = context;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    FavoriteMovieContract.View getView() {
        return mView;
    }

    @Override
    public void prepareData(FavoriteMovieViewModel favoriteMovieViewModel) {
        favoriteMovieViewModel.setMovie();
    }

    @Override
    public void navigateView(Movies movieData) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movieData);
        context.startActivity(intent);
    }
}
