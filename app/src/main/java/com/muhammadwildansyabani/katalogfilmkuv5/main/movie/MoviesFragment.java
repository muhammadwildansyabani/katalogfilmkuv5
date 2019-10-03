package com.muhammadwildansyabani.katalogfilmkuv5.main.movie;

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
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.MoviesViewModel;

import java.util.ArrayList;

public class MoviesFragment extends Fragment implements MoviesContract.View, View.OnClickListener {
    private MoviesContract.Presenter presenter;
    private RecyclerView moviesRecyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private Button reloadButton;

    private MoviesViewModel moviesViewModel;

    public MoviesViewModel getMoviesViewModel() {
        return moviesViewModel;
    }

    public MoviesContract.Presenter getPresenter() {
        return presenter;
    }

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        moviesRecyclerView = root.findViewById(R.id.movie_recycler_view);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout_movie);
        reloadButton = root.findViewById(R.id.movie_reload_button);

        reloadButton.setOnClickListener(this);
        shimmerFrameLayout.startShimmer();

        presenter = new MoviesPresenter(this, getContext());
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getMovieList().observe(this,getMovie);
        presenter.prepareData(moviesViewModel);
        return root;
    }

    private final Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movies> movies) {
            if(movies != null){
                showMovieList(movies);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    @Override
    public void showMovieList(ArrayList<Movies> moviesArrayList) {
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        MoviesRecyclerViewAdapter moviesRecyclerViewAdapter =
                new MoviesRecyclerViewAdapter(getContext(), (MoviesPresenter) presenter);
        moviesRecyclerViewAdapter.setMovieData(moviesArrayList);

        moviesRecyclerView.setAdapter(moviesRecyclerViewAdapter);
        if(presenter.onAllDataFinishLoaded()) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showReloadButton(boolean state) {
        reloadButton.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.movie_reload_button){
            presenter.prepareData(moviesViewModel);
            showReloadButton(false);
        }
    }
}
