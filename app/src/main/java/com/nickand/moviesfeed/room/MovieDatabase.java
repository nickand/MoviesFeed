package com.nickand.moviesfeed.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nickand.moviesfeed.model.ViewModel;

@Database(entities = {ViewModel.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
