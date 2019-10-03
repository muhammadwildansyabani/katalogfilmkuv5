package com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model.Movies;

import org.json.JSONException;
import org.json.JSONObject;

public class Tv extends Movies {
    private String totalEpisode;

    public String getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(String totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.totalEpisode);
    }

    public Tv(){}

    public Tv(JSONObject tvObject){
        try {
            id = tvObject.getInt("id");
            title = tvObject.getString("name");
            desc = tvObject.getString("overview");
            releaseDate = tvObject.getString("first_air_date");
            score = tvObject.getDouble("vote_average");
            imagePath = tvObject.getString("poster_path");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Tv(Parcel in) {
        super(in);
        this.totalEpisode = in.readString();
    }

    public static final Parcelable.Creator<Tv> CREATOR = new Parcelable.Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel source) {
            return new Tv(source);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };
}
