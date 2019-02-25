package com.nickand.moviesfeed.favorites.mvp.model;

import com.nickand.moviesfeed.favorites.mvp.FavoriteMVP;
import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.movies.mvp.MoviesMVP;
import com.nickand.moviesfeed.repository.Repository;

import io.reactivex.Observable;

public class FavoritesModel implements FavoriteMVP.Model {

    private Repository repository;

    public FavoritesModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return repository.getDataResult();
    }
}

