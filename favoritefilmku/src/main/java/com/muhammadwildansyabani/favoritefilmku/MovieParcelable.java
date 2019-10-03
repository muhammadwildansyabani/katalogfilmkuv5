package com.muhammadwildansyabani.favoritefilmku;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.muhammadwildansyabani.favoritefilmku.Constant.COLUMN_DESC;
import static com.muhammadwildansyabani.favoritefilmku.Constant.COLUMN_GENRE;
import static com.muhammadwildansyabani.favoritefilmku.Constant.COLUMN_RELEASE_DATE;
import static com.muhammadwildansyabani.favoritefilmku.Constant.COLUMN_RUNTIME;
import static com.muhammadwildansyabani.favoritefilmku.Constant.COLUMN_STATUS;
import static com.muhammadwildansyabani.favoritefilmku.Constant.COLUMN_TITLE;

public class MovieParcelable implements Parcelable {
    private final long id;
    private final String title;
    private final String desc;
    private final String genre;
    private final String releaseDate;
    private final String status;
    private final String runtime;
    private final double score;

    String getTitle() {
        return title;
    }

    String getDesc() {
        return desc;
    }

    String getGenre() {
        return genre;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    String getRuntime() {
        return runtime;
    }

    double getScore() {
        return score;
    }

    MovieParcelable(Cursor cursor){
        this.id = DatabaseContract.getColumnLong(cursor);
        this.title = DatabaseContract.getColumnString(cursor, COLUMN_TITLE);
        this.desc = DatabaseContract.getColumnString(cursor, COLUMN_DESC);
        this.genre = DatabaseContract.getColumnString(cursor, COLUMN_GENRE);
        this.releaseDate = DatabaseContract.getColumnString(cursor, COLUMN_RELEASE_DATE);
        this.status = DatabaseContract.getColumnString(cursor, COLUMN_STATUS);
        this.runtime = DatabaseContract.getColumnString(cursor, COLUMN_RUNTIME);
        this.score = DatabaseContract.getColumnInt(cursor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.genre);
        dest.writeString(this.releaseDate);
        dest.writeString(this.status);
        dest.writeString(this.runtime);
        dest.writeDouble(this.score);
    }

    private MovieParcelable(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.desc = in.readString();
        this.genre = in.readString();
        this.releaseDate = in.readString();
        this.status = in.readString();
        this.runtime = in.readString();
        this.score = in.readDouble();
    }

    public static final Parcelable.Creator<MovieParcelable> CREATOR = new Parcelable.Creator<MovieParcelable>() {
        @Override
        public MovieParcelable createFromParcel(Parcel source) {
            return new MovieParcelable(source);
        }

        @Override
        public MovieParcelable[] newArray(int size) {
            return new MovieParcelable[size];
        }
    };
}
