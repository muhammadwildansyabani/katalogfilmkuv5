package com.muhammadwildansyabani.katalogfilmkuv5.favorite.tv;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammadwildansyabani.katalogfilmkuv5.R;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.tv.TvDaoTask;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.tv.TvShowEntity;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;

import java.util.ArrayList;

public class FavoriteTvFragment extends Fragment implements FavoriteTvContract.View  {
    private View rootView;
    private FavoriteTvContract.Presenter mPresenter;
    private FavoriteTvViewModel favoriteTvViewModel;
    private RecyclerView favoriteRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView();
        favoriteTvViewModel = ViewModelProviders.of(this).get(FavoriteTvViewModel.class);
        favoriteTvViewModel.getTvShowList().observe(this,getTvShow);
        mPresenter.prepareData(favoriteTvViewModel);
        return rootView;
    }

    private final Observer<ArrayList<Tv>> getTvShow = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Tv> tvs) {
            if(tvs != null){
                showFavoriteData(tvs);
            }else{
                Toast.makeText(
                        getContext(),
                        getResources().getString(R.string.message_empty_favorite_list),
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    };

    @Override
    public void initView() {
        mPresenter = new FavoriteTvPresenter(this, getContext());
        favoriteRecyclerView = rootView.findViewById(R.id.favorite_item_recycler_view);
    }

    @Override
    public void showFavoriteData(ArrayList<Tv> tvArrayList) {
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FavoriteTvRecyclerViewAdapter adapter =
                new FavoriteTvRecyclerViewAdapter(getContext(), (FavoriteTvPresenter) mPresenter);
        adapter.setFavoriteTvData(tvArrayList);
        favoriteRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showAlertDialog(final Tv tv) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(getResources().getString(R.string.alert_delete_title));
        alertDialog.setMessage(getResources().getString(R.string.delete_confirmation_message));
        alertDialog.setPositiveButton(getResources().getString(R.string.yes_button_text),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        TvShowEntity tvShowEntity =
                                new TvShowEntity(
                                        tv.getId(),
                                        tv.getTitle(),
                                        tv.getDesc(),
                                        tv.getGenres().toString(),
                                        tv.getReleaseDate(),
                                        tv.getStatus(),
                                        tv.getRuntime(),
                                        tv.getTotalEpisode(),
                                        tv.getScore()
                                );
                        TvDaoTask tvDaoTask = new TvDaoTask();
                        tvDaoTask.setTvShowEntities(tvShowEntity);
                        tvDaoTask.execute(Constant.DELETE_TV_SHOW);
                        mPresenter.prepareData(favoriteTvViewModel);
                    }
                });
        alertDialog.setNegativeButton(getResources().getString(R.string.no_button_text),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog deleteAlert = alertDialog.create();
        deleteAlert.show();
    }


    @Override
    public void setPresenter(FavoriteTvContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
