package com.muhammadwildansyabani.katalogfilmkuv5.repository.tv;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TvShowDao {
    @Query("SELECT * FROM tvShow")
    List<TvShowEntity> getAll();

    @Insert
    void insertAll(TvShowEntity... tvShowEntities);

    @Delete
    void delete(TvShowEntity tvShowEntity);

    @Query("SELECT * FROM tvShow WHERE id = :id")
    TvShowEntity find(long id);
}
