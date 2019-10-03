package com.muhammadwildansyabani.katalogfilmkuv5.main.tv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.muhammadwildansyabani.katalogfilmkuv5.R;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.TvShowViewModel;

import java.util.ArrayList;

public class TvShowFragment extends Fragment implements TvShowContract.View, View.OnClickListener {
    private TvShowContract.Presenter presenter;
    private RecyclerView tvRecyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private Button reloadButton;

    private TvShowViewModel tvShowViewModel;

    public TvShowViewModel getTvShowViewModel() {
        return tvShowViewModel;
    }

    public TvShowContract.Presenter getPresenter() {
        return presenter;
    }

    public TvShowFragment() {
        presenter = new TvShowPresenter(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tv_show, container, false);
        tvRecyclerView = root.findViewById(R.id.tv_recycler_view);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout_tv);
        reloadButton = root.findViewById(R.id.tv_reload_button);

        shimmerFrameLayout.startShimmer();
        reloadButton.setOnClickListener(this);
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShowList().observe(this, getTvShow);
        presenter.prepareData(tvShowViewModel);
        return root;
    }

    private final Observer<ArrayList<Tv>> getTvShow = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Tv> tvs) {
            if (tvs != null){
                showTvList(tvs);
            }
        }
    };

    @Override
    public void showTvList(ArrayList<Tv> tvArrayList) {
        tvRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(getContext(), (TvShowPresenter) presenter);
        adapter.setTvData(tvArrayList);
        tvRecyclerView.setAdapter(adapter);

        if(presenter.onAllDataFinishLoaded()){
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showReloadButton(boolean state) {
        reloadButton.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setPresenter(TvShowContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_reload_button){
            presenter.prepareData(tvShowViewModel);
            showReloadButton(false);
        }
    }
}
