package com.muhammadwildansyabani.katalogfilmkuv5.favorite.movie;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammadwildansyabani.katalogfilmkuv5.R;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Utils;

import java.util.ArrayList;

public class FavoriteMovieRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteMovieRecyclerViewAdapter.FavoriteMovieViewHolder> {
    private final Context context;
    private final FavoriteMoviePresenter favoriteMoviePresenter;
    private ArrayList<Movies> moviesArrayList = new ArrayList<>();

    FavoriteMovieRecyclerViewAdapter(Context context, FavoriteMoviePresenter favoriteMoviePresenter){
        this.context = context;
        this.favoriteMoviePresenter = favoriteMoviePresenter;
    }

    void setFavoriteMovieData (ArrayList<Movies> movieList) { moviesArrayList = movieList; }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_layout,parent,false);
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
        private final ConstraintLayout rowLayout;
        private final ImageView imageView;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final TextView movieScore;
        private final TextView movieDuration;

        FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            rowLayout = itemView.findViewById(R.id.favorite_item_layout);
            imageView = itemView.findViewById(R.id.favorite_item_image);
            movieTitleText = itemView.findViewById(R.id.favorite_item_title);
            movieReleaseDateText = itemView.findViewById(R.id.favorite_release_date);
            movieScore = itemView.findViewById(R.id.favorite_item_score_text_view);
            movieDuration = itemView.findViewById(R.id.favorite_item_duration_text_view);
        }

        void bindData(final Movies movie){
            Bitmap image = Utils.loadImageFromStorage(
                    context.getDir(Constant.LOCAL_IMAGE_FILE_PATH, Context.MODE_PRIVATE).getPath(),
                    String.valueOf(movie.getId())
            );
            imageView.setImageBitmap(image != null ? image : Utils.getImageBitmap(movie));
            movieTitleText.setText(movie.getTitle());
            movieReleaseDateText.setText(Utils.formatDate(movie.getReleaseDate()));
            movieScore.setText(String.valueOf(movie.getScore()));
            movieDuration.setText(movie.getRuntime());
            rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteMoviePresenter.navigateView(movie);
                }
            });
            rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    favoriteMoviePresenter.getView().showAlertDialog(movie);
                    return false;
                }
            });
        }
    }
}
