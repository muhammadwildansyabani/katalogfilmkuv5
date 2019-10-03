package com.muhammadwildansyabani.katalogfilmkuv5.main.movie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.muhammadwildansyabani.katalogfilmkuv5.R;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Utils;

import java.util.ArrayList;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder> {
    private final Context context;
    private final MoviesPresenter moviesPresenter;
    private ArrayList<Movies> moviesArrayList = new ArrayList<>();

    MoviesRecyclerViewAdapter(Context context, MoviesPresenter moviesPresenter){
        this.context = context;
        this.moviesPresenter = moviesPresenter;
    }

    public void setMovieData(ArrayList<Movies> movieList){
        moviesArrayList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MovieViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bindData(moviesArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    protected class MovieViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final FrameLayout imageContainer;
        private final TextView scoreText;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            movieTitleText = itemView.findViewById(R.id.txt_title);
            movieReleaseDateText = itemView.findViewById(R.id.txt_date);
            imageContainer = itemView.findViewById(R.id.image_container);
            scoreText = itemView.findViewById(R.id.txt_score);
        }

        @SuppressLint("DefaultLocale")
        void bindData(final Movies movie){
            Glide.with(context)
                    .load(Utils.getImageBitmap(movie))
                    .placeholder(R.drawable.broken_image_24)
                    .apply(new RequestOptions()
                            .override(context
                                            .getResources()
                                            .getDimensionPixelSize(R.dimen.poster_width),
                                    context
                                            .getResources()
                                            .getDimensionPixelSize(R.dimen.poster_height))
                            .transform(new RoundedCorners(context
                                    .getResources()
                                    .getDimensionPixelSize(R.dimen.poster_corner))))
                    .into(imageView);
            movieTitleText.setText(movie.getTitle());
            movieReleaseDateText.setText(Utils.formatDate(movie.getReleaseDate()));
            scoreText.setText(String.format("%.1f", movie.getScore()));

            imageContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moviesPresenter.navigateView(movie);
                }
            });
        }
    }
}
