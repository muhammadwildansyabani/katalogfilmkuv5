package com.muhammadwildansyabani.katalogfilmkuv5.repository.movie;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant.*;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<MovieEntity> getAll();

    @Delete
    void delete(MovieEntity movies);

    @Query("SELECT * FROM movies WHERE id = :id")
    MovieEntity find(long id);

    @Query("SELECT * FROM movies")
    Cursor selectAll();

    @Insert
    long insertAll(MovieEntity movies);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = :id")
    int deleteById(long id);

    @Query("SELECT * FROM movies WHERE id = :id")
    Cursor findById(long id);

    @Update
    int update(MovieEntity movieEntity);
}
