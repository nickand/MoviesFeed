package com.nickand.moviesfeed.movies;

import com.nickand.moviesfeed.http.apimodel.Result;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class MoviesModel implements MoviesMVP.Model {

    private MovieRepository repository;

    public MoviesModel(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(repository.getResultData(), repository.getCountryData(),
            new BiFunction<Result, String, ViewModel>() {
                @Override
                public ViewModel apply(Result result, String country) {
                    //TODO change result.toString when the data POJO is created
                    return new ViewModel(result.getTitle(), country);
                }
            });

    }
}

