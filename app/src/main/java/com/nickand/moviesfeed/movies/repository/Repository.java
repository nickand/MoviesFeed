package com.nickand.moviesfeed.movies.repository;

import com.nickand.moviesfeed.http.apimodel.Result;

import io.reactivex.Observable;

public interface Repository {

    Observable<Result> getResultFromNetwork();
    Observable<Result> getResultFromCache();
    Observable<Result> getResultData();

    Observable<String> getCountryFromNetwork();
    Observable<String> getCountryFromCache();
    Observable<String> getCountryData();

    Observable<String> getImageFromNetwork();
    Observable<String> getImageFromCache();
    Observable<String> getImageData();
}
