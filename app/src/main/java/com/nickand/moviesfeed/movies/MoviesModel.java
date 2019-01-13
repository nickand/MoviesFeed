package com.nickand.moviesfeed.movies;

import com.nickand.moviesfeed.http.apimodel.Result;

import io.reactivex.Observable;
import io.reactivex.functions.Function3;

public class MoviesModel implements MoviesMVP.Model {

    private Repository repository;

    public MoviesModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(repository.getResultData(), repository.getCountryData(),
            repository.getImageData(), new Function3<Result, String, String, ViewModel>() {
                @Override
                public ViewModel apply(Result result, String country, String image) {
                    return new ViewModel(result.getTitle(), country, image);
                }
            });
    }
}

