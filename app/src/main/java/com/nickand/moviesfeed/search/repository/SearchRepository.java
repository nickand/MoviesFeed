package com.nickand.moviesfeed.search.repository;

import com.nickand.moviesfeed.http.apimodel.OmdbAPI;
import com.nickand.moviesfeed.http.apimodel.Result;
import com.nickand.moviesfeed.http.apimodel.TopMoviesRated;
import com.nickand.moviesfeed.http.services.MoviesApiService;
import com.nickand.moviesfeed.http.services.MoviesExtraInfoApisService;
import com.nickand.moviesfeed.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class SearchRepository implements Repository {

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApisService moviesExtraInfoApisService;

    private List<String> countries;
    private List<String> images;
    private List<Result> results;
    private String stringTitle;
    private long lastTimestamp;

    private static final long CACHE_LIFETIME = 20 * 1000;

    public SearchRepository(MoviesApiService mService, MoviesExtraInfoApisService eService,
                            String stringTitleMovie) {
        moviesApiService = mService;
        moviesExtraInfoApisService = eService;
        stringTitle = stringTitleMovie;

        this.lastTimestamp = System.currentTimeMillis();

        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<Result> getResultFromNetwork() {
        Observable<TopMoviesRated> topMoviesRatedObservable =
            moviesApiService.getTopMoviesRated(1);

        return topMoviesRatedObservable
            .concatMap(new Function<TopMoviesRated, Observable<Result>>() {
                @Override
                public Observable<Result> apply(TopMoviesRated topMoviesRated) {
                    return Observable.fromIterable(topMoviesRated.getResults());
                }
            }).filter(new Predicate<Result>() {
                @Override
                public boolean test(Result result) {
                    result.setTitle(stringTitle);
                    return true;
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

    @Override
    public Observable<String> getImageFromNetwork() {
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

    @Override
    public Observable<String> getImageFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(images);
        } else {
            lastTimestamp = System.currentTimeMillis();
            images.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getImageData() {
        return getImageFromCache().switchIfEmpty(getImageFromNetwork());
    }

    public Observable<String> getTitleFromNetwork() {
        Observable<OmdbAPI> movie = moviesExtraInfoApisService.getExtraInfoMovie(stringTitle);
        return movie.concatMap(new Function<OmdbAPI, Observable<String>>() {
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

    public Observable<String> getTitleFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(images);
        } else {
            lastTimestamp = System.currentTimeMillis();
            images.clear();
            return Observable.empty();
        }
    }

    public Observable<String> getTitleData() {
        return getTitleFromCache().switchIfEmpty(getTitleFromNetwork());
    }
}
