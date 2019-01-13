package com.nickand.moviesfeed.movies;

import com.nickand.moviesfeed.http.MoviesApiService;
import com.nickand.moviesfeed.http.MoviesExtraInfoApisService;
import com.nickand.moviesfeed.http.apimodel.OmdbAPI;
import com.nickand.moviesfeed.http.apimodel.Result;
import com.nickand.moviesfeed.http.apimodel.TopMoviesRated;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MoviesRepository implements Repository {

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApisService moviesExtraInfoApisService;

    private List<String> countries;
    private List<Result> results;
    private long lastTimestamp;

    private static final long CACHE_LIFETIME = 20 * 1000;

    public MoviesRepository(MoviesApiService mService, MoviesExtraInfoApisService eService) {
        moviesApiService = mService;
        moviesExtraInfoApisService = eService;

        this.lastTimestamp = System.currentTimeMillis();

        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<Result> getResultFromNetwork() {
        Observable<TopMoviesRated> topMoviesRatedObservable =
            moviesApiService.getTopMoviesRated(1)
                .concatWith(moviesApiService.getTopMoviesRated(2))
                .concatWith(moviesApiService.getTopMoviesRated(3));

        return topMoviesRatedObservable
            .concatMap(new Function<TopMoviesRated, Observable<Result>>() {
                @Override
                public Observable<Result> apply(TopMoviesRated topMoviesRated) {
                    return Observable.fromIterable(topMoviesRated.getResults());
                }
            }).doOnNext(new Consumer<Result>() {
                @Override
                public void accept(Result result) {
                    results.add(result);
                }
            });
    }

    @Override
    public Observable<Result> getResultFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(results);
        } else {
            lastTimestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Result> getResultData() {
        return getResultFromCache().switchIfEmpty(getResultFromNetwork());
    }

    @Override
    public Observable<String> getCountryFromNetwork() {
        Observable<Result> movies = getResultFromNetwork();
        return movies.concatMap(new Function<Result, Observable<OmdbAPI>>() {
            @Override
            public Observable<OmdbAPI> apply(Result result) {
                return moviesExtraInfoApisService.getExtraInfoMovie(result.getTitle());
            }
        }).concatMap(new Function<OmdbAPI, Observable<String>>() {
            @Override
            public Observable<String> apply(OmdbAPI omdbAPI) {
                if (omdbAPI == null || omdbAPI.getCountry() == null) {
                    return Observable.just("No country");
                } else {
                    return Observable.just(omdbAPI.getCountry());
                }
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String country) {
                countries.add(country);
            }
        });
    }

    @Override
    public Observable<String> getCountryFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(countries);
        } else {
            lastTimestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountryData() {
        return getCountryFromCache().switchIfEmpty(getCountryFromNetwork());
    }
}
