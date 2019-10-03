package com.muhammadwildansyabani.katalogfilmkuv5.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.muhammadwildansyabani.katalogfilmkuv5.R;
import com.muhammadwildansyabani.katalogfilmkuv5.customview.GenreTextView;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Genre;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.db.LoadFilmCallback;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.movie.MovieDaoTask;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.movie.MovieEntity;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.tv.TvDaoTask;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.tv.TvShowEntity;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_DESC;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_ID;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_RELEASE_DATE;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_RUNTIME;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_TITLE;

public class DetailActivity extends AppCompatActivity
        implements DetailMovieContract.View, View.OnClickListener, LoadFilmCallback {

    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_TV = "tv";
    private DetailMovieContract.Presenter mPresenter;

    private ImageView poster;
    private TextView episodeText;
    private TextView titleEpisodeText;
    private TextView titleText;
    private TextView descText;
    private LinearLayout genreGridView;
    private TextView statusReleaseText;
    private TextView scoreText;
    private TextView runtimeText;
    private ImageView favoriteButton;

    private ShimmerFrameLayout shimmerLayout;
    private ScrollView scrollView;

    private Movies movieData;
    private Movies movie;
    private Tv tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(R.string.detail_film_title);
        AndroidNetworking.initialize(this);
        mPresenter = new DetailPresenter(this);
        initView();

    }
    @Override
    public void initView() {
        movie = mPresenter.retrieveIntentMovieData(getIntent());
        tv = mPresenter.retrieveIntentTvData(getIntent());
        poster = findViewById(R.id.img_detail);
        episodeText = findViewById(R.id.eps_tv);
        titleEpisodeText = findViewById(R.id.title_eps_tv);
        titleText = findViewById(R.id.detail_title);
        descText = findViewById(R.id.desc_tv);
        genreGridView = findViewById(R.id.genre_container);
        statusReleaseText = findViewById(R.id.status_tv);
        scoreText = findViewById(R.id.score_tv);
        runtimeText = findViewById(R.id.runtime_tv);
        shimmerLayout = findViewById(R.id.shimmer_layout_detail_page);
        scrollView = findViewById(R.id.detail_page_scroll_view);
        favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(this);
        if (tv == null) {
            favoriteButton.setImageResource(checkMovieOnFavoriteList(movie) ?
                    R.drawable.favorite_white_36dp : R.drawable.favorite_border_white_36dp);
        }else
            favoriteButton.setImageResource(checkTvOnFavoriteList(tv) ?
                    R.drawable.favorite_white_36dp : R.drawable.favorite_border_white_36dp);
    }


    private boolean checkMovieOnFavoriteList(Movies movie) {
        MovieDaoTask task = new MovieDaoTask();
        try {
            long findId = 0;
            task.setId(movie.getId());
            List<MovieEntity> findEntity = task.execute(Constant.FIND_MOVIE).get();
            if (!findEntity.isEmpty())
                findId = findEntity.get(0).getId();

            return movie.getId() == findId;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkTvOnFavoriteList(Tv tv) {
        TvDaoTask task = new TvDaoTask();
        try {
            long findId = 0;
            task.setId(tv.getId());
            List<TvShowEntity> findTvEntity = task.execute(Constant.FIND_TV_SHOW).get();
            if (!findTvEntity.isEmpty())
                findId = findTvEntity.get(0).getId();

            return tv.getId() == findId;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading(true);
        showMovieDetailInfo();
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void showMovieDetailInfo() {
        Movies movie = mPresenter.retrieveIntentMovieData(getIntent());
        Tv tv = mPresenter.retrieveIntentTvData(getIntent());
        String category = Constant.URL_MOVIES;
        if (tv == null)
            episodeText.setVisibility(View.GONE);
        else {
            movie = tv;
            category = Constant.URL_TV;
            episodeText.setText(String.format("Tv Shows | %s", tv.getTotalEpisode()));
        }

        if (tv == null) {
            titleEpisodeText.setVisibility(View.GONE);
        }

        mPresenter.prepareFilmDetails(movie, category);
        Bitmap image = Utils.loadImageFromStorage(
                getDir(Constant.LOCAL_IMAGE_FILE_PATH, Context.MODE_PRIVATE).getPath(),
                String.valueOf(movie.getId())
        );
        poster.setImageBitmap(image != null ? image : Utils.getImageBitmap(movie));
        titleText.setText(movie.getTitle());
        descText.setText(movie.getDesc());
        scoreText.setText(String.format("%.1f", movie.getScore()));

    }

    @Override
    public void showFilmDetailsData(Object filmData) {
        if (filmData instanceof Tv) {
            episodeText.setText(String.format("Tv Shows | %s Episode",
                    ((Tv) filmData).getTotalEpisode()));
        }
        statusReleaseText.setText(((Movies) filmData).getStatus());
        runtimeText.setText(((Movies) filmData).getRuntime());
        showGenreList(((Movies) filmData).getGenres());
        showLoading(false);
        movieData = (Movies) filmData;
    }

    @Override
    public void showLoading(boolean state) {
        if (state) {
            shimmerLayout.startShimmer();
            scrollView.setVisibility(View.GONE);
            shimmerLayout.setVisibility(View.VISIBLE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            shimmerLayout.startShimmer();
            shimmerLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToastMessage(String message) {
        message = message.contains(Constant.INSERT) ?
                message.equals(Constant.SUCCESS_INSERT) ?
                        getResources().getString(R.string.success_add_to_favorite_text) :
                        getResources().getString(R.string.error_add_favorite_text)
                : message.equals(Constant.SUCCESS_DELETE) ?
                getResources().getString(R.string.success_remove_from_favorite_text) :
                getResources().getString(R.string.error_remove_from_favorite_text);
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void updateButtonImageState(boolean flag) {
        favoriteButton.setImageResource(flag ?
                R.drawable.favorite_white_36dp :
                R.drawable.favorite_border_white_36dp
        );
    }

    @Override
    public void setPresenter(DetailMovieContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showGenreList(ArrayList<Genre> genreList) {
        genreGridView.removeAllViews();
        LinearLayout row = new LinearLayout(this);
        for (int idx = 0; idx < genreList.size(); idx++) {
            if (idx % 3 == 0) {
                row = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(layoutParams);
            }
            GenreTextView genreText = new GenreTextView(this);
            genreText.setText(genreList.get(idx).getName());
            row.addView(genreText);
            if (idx % 3 == 0) {
                genreGridView.addView(row);
            }
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.favorite_button){
            if (tv == null){
                if(checkMovieOnFavoriteList(movie)){
                    mPresenter.removeFromMovieFavorite(movie,movieData);
                }else{
                    mPresenter.addToMovieFavorite(movie,movieData);
                }
            }
            else {
                if (checkTvOnFavoriteList(tv)) {
                    mPresenter.removeFromTvFavorite(tv,movieData);
                }else{
                    mPresenter.addToTvFavorite(tv,movieData);
                }
            }

        }
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(Cursor film) {
        while(film.moveToNext()){
            int id = film.getInt(film.getColumnIndexOrThrow(COLUMN_ID));
            String title = film.getString(film.getColumnIndexOrThrow(COLUMN_TITLE));
            String description = film.getString(film.getColumnIndexOrThrow(COLUMN_DESC));
            String date = film.getString(film.getColumnIndexOrThrow(COLUMN_RELEASE_DATE));
            String runtime = film.getString(film.getColumnIndexOrThrow(COLUMN_RUNTIME));

        }

    }
}
