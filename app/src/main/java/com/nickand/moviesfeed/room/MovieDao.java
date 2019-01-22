package com.nickand.moviesfeed.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nickand.moviesfeed.model.ViewModel;

import java.util.List;

import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Insert(onConflict = REPLACE)
    void insert(ViewModel viewModel);

    @Delete
    void delete(ViewModel viewModel);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    Single<ViewModel> load(int movieId);

    @Query("SELECT * FROM movies ORDER BY movie_id DESC")
    Single<List<ViewModel>> getAllMovies();
}
