package com.muhammadwildansyabani.katalogfilmkuv5.repository.movie;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.muhammadwildansyabani.katalogfilmkuv5.repository.db.DatabaseContract;

import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_DESC;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_GENRE;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_ID;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_RELEASE_DATE;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_RUNTIME;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_SCORE;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_STATUS;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.COLUMN_TITLE;
import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class MovieEntity {
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_TITLE)
    private final String title;

    @ColumnInfo(name = COLUMN_DESC)
    private final String desc;

    @ColumnInfo(name = COLUMN_GENRE)
    private final String genre;

    @ColumnInfo(name = COLUMN_RELEASE_DATE)
    private final String releaseDate;

    @ColumnInfo(name = COLUMN_STATUS)
    private final String status;

    @ColumnInfo(name = COLUMN_RUNTIME)
    private final String runtime;

    @ColumnInfo(name = COLUMN_SCORE)
    private final double score;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getGenre() {
        return genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public String getRuntime() {
        return runtime;
    }

    public double getScore() {
        return score;
    }

    public void setId(long id){
        this.id = id;
    }

    public MovieEntity(long id, String title, String desc, String genre,
                       String releaseDate, String status, String runtime,
                       double score
    ) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.status = status;
        this.runtime = runtime;
        this.score = score;
    }

    public MovieEntity(ContentValues values){
        this.id = values.containsKey(COLUMN_ID) ? values.getAsLong(COLUMN_ID) : 0;
        this.title = values.containsKey(COLUMN_TITLE) ? values.getAsString(COLUMN_TITLE) : "";
        this.desc =values.containsKey(COLUMN_DESC) ? values.getAsString(COLUMN_DESC) : "";
        this.genre =values.containsKey(COLUMN_GENRE) ? values.getAsString(COLUMN_GENRE) : "";
        this.releaseDate =values.containsKey(COLUMN_RELEASE_DATE) ? values.getAsString(COLUMN_RELEASE_DATE) : "";
        this.status =values.containsKey(COLUMN_STATUS) ? values.getAsString(COLUMN_STATUS) : "";
        this.runtime =values.containsKey(COLUMN_RUNTIME) ? values.getAsString(COLUMN_RUNTIME) : "";
        this.score =values.containsKey(COLUMN_SCORE) ? values.getAsDouble(COLUMN_SCORE) : 0.0;
    }

    public MovieEntity(Cursor cursor){
        this.id = DatabaseContract.getColumnLong(cursor, COLUMN_ID);
        this.title = DatabaseContract.getColumnString(cursor, COLUMN_TITLE);
        this.desc = DatabaseContract.getColumnString(cursor, COLUMN_DESC);
        this.genre = DatabaseContract.getColumnString(cursor, COLUMN_GENRE);
        this.releaseDate = DatabaseContract.getColumnString(cursor, COLUMN_RELEASE_DATE);
        this.status = DatabaseContract.getColumnString(cursor, COLUMN_STATUS);
        this.runtime = DatabaseContract.getColumnString(cursor, COLUMN_RUNTIME);
        this.score = DatabaseContract.getColumnInt(cursor, COLUMN_SCORE);
    }
}
