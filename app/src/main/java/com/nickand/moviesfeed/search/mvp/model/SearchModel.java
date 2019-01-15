package com.nickand.moviesfeed.search.mvp.model;

import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.search.mvp.SearchMVP;
import com.nickand.moviesfeed.search.repository.SearchRepository;

import io.reactivex.Observable;
import io.reactivex.functions.Function3;

public class SearchModel implements SearchMVP.Model {

    private SearchRepository repository;

    public SearchModel(SearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result(String titleMovie) {
        return repository.getMyShit(titleMovie);
    }
}

