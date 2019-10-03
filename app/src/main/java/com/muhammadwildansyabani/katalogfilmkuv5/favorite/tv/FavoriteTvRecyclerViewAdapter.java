package com.muhammadwildansyabani.katalogfilmkuv5.favorite.tv;

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
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Utils;

import java.util.ArrayList;

public class FavoriteTvRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteTvRecyclerViewAdapter.FavoriteTvViewHolder> {
    private final Context context;
    private final FavoriteTvPresenter favoriteTvPresenter;
    private ArrayList<Tv> tvArrayList = new ArrayList<>();

    FavoriteTvRecyclerViewAdapter(Context context, FavoriteTvPresenter favoriteTvPresenter) {
        this.context = context;
        this.favoriteTvPresenter = favoriteTvPresenter;
    }

    void setFavoriteTvData(ArrayList<Tv> tvList) {
        tvArrayList = tvList;
    }

    @NonNull
    @Override
    public FavoriteTvRecyclerViewAdapter.FavoriteTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_layout, parent, false);
        return new FavoriteTvRecyclerViewAdapter.FavoriteTvViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvRecyclerViewAdapter.FavoriteTvViewHolder favoriteMovieViewHolder, int position) {
        favoriteMovieViewHolder.bindData(tvArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvArrayList.size();
    }

    class FavoriteTvViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout rowLayout;
        private final ImageView imageView;
        private final TextView tvShowTitleText;
        private final TextView tvShowReleaseDateText;
        private final TextView tvShowScore;
        private final TextView tvShowDuration;

        FavoriteTvViewHolder(@NonNull View itemView) {
            super(itemView);
            rowLayout = itemView.findViewById(R.id.favorite_item_layout);
            imageView = itemView.findViewById(R.id.favorite_item_image);
            tvShowTitleText = itemView.findViewById(R.id.favorite_item_title);
            tvShowReleaseDateText = itemView.findViewById(R.id.favorite_release_date);
            tvShowScore = itemView.findViewById(R.id.favorite_item_score_text_view);
            tvShowDuration = itemView.findViewById(R.id.favorite_item_duration_text_view);
        }

        void bindData(final Tv tv) {
            Bitmap image = Utils.loadImageFromStorage(
                    context.getDir(Constant.LOCAL_IMAGE_FILE_PATH, Context.MODE_PRIVATE).getPath(),
                    String.valueOf(tv.getId())
            );
            imageView.setImageBitmap(image != null ? image : Utils.getImageBitmap(tv));
            tvShowTitleText.setText(tv.getTitle());
            tvShowReleaseDateText.setText(Utils.formatDate(tv.getReleaseDate()));
            tvShowScore.setText(String.valueOf(tv.getScore()));
            tvShowDuration.setText(tv.getRuntime());
            rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteTvPresenter.navigateView(tv);
                }
            });
            rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    favoriteTvPresenter.getView().showAlertDialog(tv);
                    return false;
                }
            });
        }
    }
}
