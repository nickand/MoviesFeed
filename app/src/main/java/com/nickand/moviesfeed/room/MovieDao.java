package com.nickand.moviesfeed.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nickand.moviesfeed.model.ViewModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Insert(onConflict = REPLACE)
    void save(ViewModel viewModel);

    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    LiveData<ViewModel> load(int movieId);

    @Query("SELECT * FROM movies ORDER BY movie_id DESC")
    LiveData<List<ViewModel>> getAllMovies();
}
