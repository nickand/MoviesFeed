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
    public Observable<ViewModel> result(Observable<String> titleMovie) {
        return Observable.zip(repository.getTitleData(), repository.getCountryData(),
            repository.getImageData(), new Function3<String, String, String, ViewModel>() {
                @Override
                public ViewModel apply(String title, String country, String image) {
                    return new ViewModel(title, country, image);
                }
            });
    }
}

