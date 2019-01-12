package com.nickand.moviesfeed.movies;

import io.reactivex.Observable;

public interface MovieRepository {

    Observable<Result> getResultData();
    Observable<String> getCountryData();
}
