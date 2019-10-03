package com.muhammadwildansyabani.katalogfilmkuv5.favorite.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.tv.TvDaoTask;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.tv.TvShowEntity;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTvViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Tv>> tvShowList = new MutableLiveData<>();


    public void setTvShow() {
        TvDaoTask task = new TvDaoTask();
        try{
            tvShowList.setValue(getFavoriteData(task.execute(Constant.GET_FAVORITE_TV_SHOW).get()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<Tv> getFavoriteData(List<TvShowEntity> tvEntities) {
        ArrayList<Tv> tvsArrayList = new ArrayList<>();
        for(TvShowEntity tvEntity : tvEntities){
            Tv tv = new Tv();
            tv.setId(tvEntity.getId());
            tv.setTitle(tvEntity.getTitle());
            tv.setDesc(tvEntity.getDesc());
            tv.setReleaseDate(tvEntity.getReleaseDate());
            tv.setRuntime(tvEntity.getRuntime());
            tv.setTotalEpisode(tvEntity.getTotalEpisode());
            tv.setScore(tvEntity.getScore());
            tvsArrayList.add(tv);
        }
        return tvsArrayList;
    }
    public LiveData<ArrayList<Tv>> getTvShowList(){
        return tvShowList;
    }
}
