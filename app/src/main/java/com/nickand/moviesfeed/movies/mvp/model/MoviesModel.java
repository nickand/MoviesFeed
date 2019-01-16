package com.nickand.moviesfeed.movies.mvp.model;

import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.movies.mvp.MoviesMVP;
import com.nickand.moviesfeed.repository.Repository;

import io.reactivex.Observable;

public class MoviesModel implements MoviesMVP.Model {

    private Repository repository;

    public MoviesModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return repository.getDataResult();
    }
}

