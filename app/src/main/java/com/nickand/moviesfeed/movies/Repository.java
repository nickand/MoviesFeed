package com.nickand.moviesfeed.movies;

import com.nickand.moviesfeed.http.apimodel.Result;

import io.reactivex.Observable;

public interface Repository {

    Observable<Result> getResultData();
    Observable<String> getCountryData();
}
