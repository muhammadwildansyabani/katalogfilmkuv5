package com.muhammadwildansyabani.katalogfilmkuv5.main.tv;

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
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.Tv;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Utils;

import java.util.ArrayList;

public class TvRecyclerViewAdapter extends RecyclerView.Adapter<TvRecyclerViewAdapter.TvViewHolder> {
    private final Context context;
    private final TvShowPresenter tvShowPresenter;
    private ArrayList<Tv> tvShowsList = new ArrayList<>();

    TvRecyclerViewAdapter(Context context, TvShowPresenter tvShowPresenter){
        this.context = context;
        this.tvShowPresenter = tvShowPresenter;
    }

    public void setTvData(ArrayList<Tv> list){
        tvShowsList = list;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new TvViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder tvViewHolder, int position) {
        tvViewHolder.bindData(tvShowsList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowsList.size();
    }

    protected class TvViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final FrameLayout imageContainer;
        private final TextView scoreText;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            movieTitleText = itemView.findViewById(R.id.txt_title);
            movieReleaseDateText = itemView.findViewById(R.id.txt_date);
            imageContainer = itemView.findViewById(R.id.image_container);
            scoreText = itemView.findViewById(R.id.txt_score);
        }

        @SuppressLint("DefaultLocale")
        void bindData(final Tv tv){
            Glide.with(context)
                    .load(Utils.getImageBitmap(tv))
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
            movieTitleText.setText(tv.getTitle());
            movieReleaseDateText.setText(Utils.formatDate(tv.getReleaseDate()));
            scoreText.setText(String.format("%.1f", tv.getScore()));

            imageContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvShowPresenter.navigateView(tv);
                }
            });
        }
    }
}
