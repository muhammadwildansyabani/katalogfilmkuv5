package com.muhammadwildansyabani.katalogfilmkuv5.main.tv;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.muhammadwildansyabani.katalogfilmkuv5.detail.DetailActivity;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.TvShowViewModel;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;

public class TvShowPresenter implements TvShowContract.Presenter {
    private final TvShowContract.View mainView;
    private TvShowViewModel tvShowViewModel;

    TvShowPresenter(TvShowContract.View view){
        this.mainView = view;
        this.mainView.setPresenter(this);
    }

    @Override
    public void prepareData(TvShowViewModel tvShowViewModel) {
        this.tvShowViewModel = tvShowViewModel;
        String url = Constant.getUrlOf(
                Constant.URL_TYPE_DISCOVER,
                Constant.URL_TV,
                "",
                ((TvShowFragment)mainView).getContext());
        this.tvShowViewModel.setTvShow(mainView, url);
    }

    @Override
    public void navigateView(Tv tvData) {
        Intent intent = new Intent(((Fragment)mainView).getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TV, tvData);
        ((Fragment) mainView).startActivity(intent);
    }

    @Override
    public boolean onAllDataFinishLoaded() {
        return tvShowViewModel.getLoadedItemCounter() >= tvShowViewModel.getTotalItem();
    }
}
