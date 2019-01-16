package com.nickand.moviesfeed.movies.repository;

import com.nickand.moviesfeed.http.apimodel.OmdbAPI;
import com.nickand.moviesfeed.http.apimodel.Result;
import com.nickand.moviesfeed.http.apimodel.TopMoviesRated;
import com.nickand.moviesfeed.http.services.MoviesApiService;
import com.nickand.moviesfeed.http.services.MoviesExtraInfoApisService;
import com.nickand.moviesfeed.repository.RepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class MoviesRepository extends RepositoryImpl<Result> {

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApisService moviesExtraInfoApisService;

    private List<String> countries;
    private List<String> images;
    private List<Result> results;
    private long lastTimestamp;

    private static final long CACHE_LIFETIME = 20 * 1000;

    public MoviesRepository(MoviesApiService mService, MoviesExtraInfoApisService eService) {
        moviesApiService = mService;
        moviesExtraInfoApisService = eService;

        this.lastTimestamp = System.currentTimeMillis();

        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    private boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    private Observable<Result> getResultFromNetwork() {

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
            })
            .filter(new Predicate<Result>() {
                @Override
                public boolean test(Result result) {
                    result.fixTitle();
                    return true;
                }
            }).doOnNext(new Consumer<Result>() {
                @Override
                public void accept(Result result) {
                    results.add(result);
                }
            });
    }

    private Observable<Result> getResultFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(results);
        } else {
            lastTimestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    private Observable<Result> getResultData() {
        return getResultFromCache().switchIfEmpty(getResultFromNetwork());
    }

    private Observable<String> getCountryFromNetwork() {
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

    private Observable<String> getCountryFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(countries);
        } else {
            lastTimestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    private Observable<String> getCountryData() {
        return getCountryFromCache().switchIfEmpty(getCountryFromNetwork());
    }

    private Observable<String> getImageFromNetwork() {
        Observable<Result> movies = getResultFromNetwork();
        return movies.concatMap(new Function<Result, Observable<OmdbAPI>>() {
            @Override
            public Observable<OmdbAPI> apply(Result result) {
                return moviesExtraInfoApisService.getExtraInfoMovie(result.getTitle());
            }
        }).concatMap(new Function<OmdbAPI, Observable<String>>() {
            @Override
            public Observable<String> apply(OmdbAPI omdbAPI) {
                if (omdbAPI == null || omdbAPI.getPoster() == null) {
                    return Observable.just("No image");
                } else {
                    return Observable.just(omdbAPI.getPoster());
                }
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String image) {
                images.add(image);
            }
        });
    }

    private Observable<String> getImageFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(images);
        } else {
            lastTimestamp = System.currentTimeMillis();
            images.clear();
            return Observable.empty();
        }
    }

    public Observable<String> getImageData() {
        return getImageFromCache().switchIfEmpty(getImageFromNetwork());
    }

    @Override
    protected Observable<Result> getDataByTitle() {
        return getResultData();
    }

    @Override
    protected Observable<String> getImageDataByTitle() {
        return getImageData();
    }

    @Override
    public Observable<String> getMovieCountryData() {
        return getCountryData();
    }
}
