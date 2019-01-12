package com.nickand.moviesfeed.movies;

import com.nickand.moviesfeed.http.MoviesApiService;
import com.nickand.moviesfeed.http.MoviesExtraInfoApisService;
import com.nickand.moviesfeed.http.apimodel.Result;

import io.reactivex.Observable;

public class MoviesRepository implements Repository {

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApisService moviesExtraInfoApisService;

    public MoviesRepository(MoviesApiService mService, MoviesExtraInfoApisService eService) {
        moviesApiService = mService;
        moviesExtraInfoApisService = eService;
    }

    @Override
    public Observable<Result> getResultData() {
        return null;
    }

    @Override
    public Observable<String> getCountryData() {
        return null;
    }
}
