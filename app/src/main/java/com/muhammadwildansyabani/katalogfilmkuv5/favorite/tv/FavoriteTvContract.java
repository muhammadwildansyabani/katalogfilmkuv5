package com.muhammadwildansyabani.katalogfilmkuv5.favorite.tv;

import com.muhammadwildansyabani.katalogfilmkuv5.BasePresenter;
import com.muhammadwildansyabani.katalogfilmkuv5.BaseView;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;

import java.util.ArrayList;

public interface FavoriteTvContract {
    interface View extends BaseView<Presenter> {
        void initView();
        void showFavoriteData(ArrayList<Tv> tvArrayList);
        void showAlertDialog(Tv tv);
    }

    interface Presenter extends BasePresenter {
        void prepareData(FavoriteTvViewModel favoriteTvViewModel);
        void navigateView(Tv tvData);
    }
}
