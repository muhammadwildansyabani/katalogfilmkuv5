package com.muhammadwildansyabani.katalogfilmkuv5.repository.tv;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tvShow")
public class TvShowEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private final long id;

    @ColumnInfo(name = "title")
    private final String title;

    @ColumnInfo(name = "desc")
    private final String desc;

    @ColumnInfo(name = "genre")
    private final String genre;

    @ColumnInfo(name = "release_date")
    private final String releaseDate;

    @ColumnInfo(name = "status")
    private final String status;

    @ColumnInfo(name = "runtime")
    private final String runtime;

    @ColumnInfo(name = "total_episode")
    private final String totalEpisode;

    @ColumnInfo(name = "score")
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

    public String getTotalEpisode() {
        return totalEpisode;
    }

    public double getScore() {
        return score;
    }

    public TvShowEntity(long id, String title, String desc, String genre,
                        String releaseDate, String status, String runtime,
                        String totalEpisode, double score) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.status = status;
        this.runtime = runtime;
        this.totalEpisode = totalEpisode;
        this.score = score;
    }
}
