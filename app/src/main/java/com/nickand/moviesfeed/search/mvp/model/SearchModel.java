package com.nickand.moviesfeed.search.mvp.model;

import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.repository.Repository;
import com.nickand.moviesfeed.search.mvp.SearchMVP;

import io.reactivex.Observable;

public class SearchModel implements SearchMVP.Model {

    private Repository repository;

    public SearchModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result(String titleMovie) {
        return repository.getDataResult(titleMovie);
    }
}

