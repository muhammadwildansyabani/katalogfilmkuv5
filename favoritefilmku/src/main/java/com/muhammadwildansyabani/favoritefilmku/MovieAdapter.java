package com.muhammadwildansyabani.favoritefilmku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.FavoriteMovieViewHolder> {

    private ArrayList<MovieParcelable> moviesArrayList = new ArrayList<>();

    MovieAdapter( ){
    }

    void setFavoriteMovieData (ArrayList<MovieParcelable> movieList) { moviesArrayList = movieList; }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_film_item,parent,false);
        return new FavoriteMovieViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder favoriteMovieViewHolder, int position) {
        favoriteMovieViewHolder.bindData(moviesArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }
    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView releaseDateText;
        private final TextView genreText;
        private final TextView overviewText;
        private final TextView movieScore;
        private final TextView movieDuration;
        FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.detail_title);
            genreText = itemView.findViewById(R.id.txt_genre);
            overviewText = itemView.findViewById(R.id.txt_overview);
            releaseDateText = itemView.findViewById(R.id.txt_date);
            movieScore = itemView.findViewById(R.id.txt_score);
            movieDuration = itemView.findViewById(R.id.txt_runtime);
        }

        void bindData(final MovieParcelable movie){
            titleText.setText(movie.getTitle());
            releaseDateText.setText(Utils.formatDate(movie.getReleaseDate()));
            genreText.setText(movie.getGenre());
            overviewText.setText(movie.getDesc());
            movieScore.setText(String.valueOf(movie.getScore()));
            movieDuration.setText(movie.getRuntime());
        }
    }
}
