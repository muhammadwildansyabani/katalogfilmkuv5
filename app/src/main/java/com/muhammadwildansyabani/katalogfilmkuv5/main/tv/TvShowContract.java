package com.muhammadwildansyabani.katalogfilmkuv5.main.tv;

import com.muhammadwildansyabani.katalogfilmkuv5.BasePresenter;
import com.muhammadwildansyabani.katalogfilmkuv5.BaseView;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.TvShowViewModel;

import java.util.ArrayList;

public interface TvShowContract {
    interface View extends BaseView<Presenter> {
        void showTvList(ArrayList<Tv> tvArrayList);
        void showReloadButton(boolean state);
    }

    interface Presenter extends BasePresenter {
        void prepareData(TvShowViewModel tvShowViewModel);
        void navigateView(Tv tvData);
        boolean onAllDataFinishLoaded();
    }
}
