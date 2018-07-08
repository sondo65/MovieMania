package com.magarex.moviemania.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.magarex.moviemania.Models.Result;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Result>> getAllMovies();

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Query("SELECT * FROM movies WHERE criteria = :criteria")
    LiveData<List<Result>> getMoviesByCriteria(String criteria);

    @Query("DELETE FROM movies WHERE criteria = :criteria")
    void deleteMoviesByCriteria(String criteria);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMoviesToDb(List<Result> movieList);

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Result> getMoviesLiveData(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Result... moviesResult);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Result moviesResult);

    @Delete
    void deleteMovies(Result moviesResult);

}