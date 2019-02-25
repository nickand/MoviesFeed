package com.nickand.moviesfeed.favorites.mvp;

import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.room.MovieDatabase;

public class FavoriteManager {

    private MovieDatabase movieDatabase;

    public FavoriteManager(MovieDatabase database) {
        this.movieDatabase = database;
    }

    public void saveFavorite(ViewModel moviesModel) {
        movieDatabase.movieDao().insert(moviesModel);
    }
}
