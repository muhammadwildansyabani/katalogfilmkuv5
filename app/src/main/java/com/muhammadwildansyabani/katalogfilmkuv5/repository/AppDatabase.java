package com.muhammadwildansyabani.katalogfilmkuv5.repository;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.muhammadwildansyabani.katalogfilmkuv5.repository.movie.MovieDao;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.movie.MovieEntity;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.tv.TvShowDao;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.tv.TvShowEntity;

@Database(entities = {MovieEntity.class, TvShowEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract TvShowDao tvShowDao();
}
