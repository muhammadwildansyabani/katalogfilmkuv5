package com.muhammadwildansyabani.katalogfilmkuv5.favorite.tv;

import android.content.Context;
import android.content.Intent;

import com.muhammadwildansyabani.katalogfilmkuv5.detail.DetailActivity;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;

public class FavoriteTvPresenter implements FavoriteTvContract.Presenter {
    private final FavoriteTvContract.View mView;
    private final Context context;

    FavoriteTvPresenter(FavoriteTvContract.View mView, Context context){
        this.mView = mView;
        this.mView.setPresenter(this);
        this.context = context;
    }

    FavoriteTvContract.View getView() {
        return mView;
    }

    @Override
    public void prepareData(FavoriteTvViewModel favoriteTvViewModel) {
        favoriteTvViewModel.setTvShow();
    }

    @Override
    public void navigateView(Tv tvData) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TV, tvData);
        context.startActivity(intent);
    }
}
