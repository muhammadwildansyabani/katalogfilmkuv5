package com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.MoviesContract;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.db.FilmImageRepository;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Movies>>  movieList = new MutableLiveData<>();
    private final ArrayList<Movies> moviesItemList = new ArrayList<>();
    private int totalItem = 0;
    private int loadedItemCounter = 0;

    public int getTotalItem() {
        return totalItem;
    }

    public int getLoadedItemCounter() {
        return loadedItemCounter;
    }

    public void setMovie(final MoviesContract.View mView, String url){
        moviesItemList.clear();
        AndroidNetworking.get(url)
                .setTag("movies")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results =  response.getJSONArray("results");
                            totalItem = results.length();
                            for(int i=0; i< totalItem;i++) {
                                JSONObject movieObject = results.getJSONObject(i);
                                final Movies moviesItem = new Movies(movieObject);
                                AndroidNetworking.get(
                                        Utils.getObjectImageUrl(
                                                Constant.IMAGE_URL,
                                                Constant.IMAGE_SIZE_W500,
                                                moviesItem.getImagePath()
                                        ))
                                        .setTag("imageRequestTag")
                                        .setPriority(Priority.HIGH)
                                        .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                        .build()
                                        .getAsBitmap(new BitmapRequestListener() {
                                            @Override
                                            public void onResponse(Bitmap response) {
                                                loadedItemCounter++;
                                                FilmImageRepository.imageList.add(response);
                                                moviesItem.setImageId(response.getGenerationId());
                                                moviesItemList.add(moviesItem);
                                                movieList.setValue(moviesItemList);

                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                loadedItemCounter++;

                                            }
                                        });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mView.showReloadButton(true);
                    }
                });

    }

    public LiveData<ArrayList<Movies>> getMovieList(){
        return movieList;
    }

}
