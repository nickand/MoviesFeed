package com.nickand.moviesfeed.search.repository;

import com.nickand.moviesfeed.http.apimodel.OmdbAPI;
import com.nickand.moviesfeed.http.apimodel.Result;
import com.nickand.moviesfeed.http.apimodel.TopMoviesRated;
import com.nickand.moviesfeed.http.services.MoviesApiService;
import com.nickand.moviesfeed.http.services.MoviesExtraInfoApisService;
import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;

public class SearchRepository implements Repository {

    private MoviesExtraInfoApisService moviesExtraInfoApisService;

    private String movieTitle;
    private String movieCountry;
    private String movieImage;
    private long lastTimestamp;

    private static final long CACHE_LIFETIME = 20 * 1000;

    public SearchRepository(MoviesExtraInfoApisService eService) {
        moviesExtraInfoApisService = eService;

        this.lastTimestamp = System.currentTimeMillis();

        movieTitle = "";
        movieCountry = "";
        movieImage = "";
    }

    public boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<Result> getResultFromNetwork() {
        return null;
    }

    @Override
    public Observable<Result> getResultFromCache() {
        return null;
    }

    @Override
    public Observable<Result> getResultData() {
        return null;
    }

    @Override
    public Observable<String> getCountryFromNetwork() {
        return null;
    }

    @Override
    public Observable<String> getCountryFromCache() {
        return null;
    }

    @Override
    public Observable<String> getCountryData() {
        return null;
    }

    @Override
    public Observable<String> getImageFromNetwork() {
        return null;
    }

    @Override
    public Observable<String> getImageFromCache() {
        return null;
    }

    @Override
    public Observable<String> getImageData() {
        return null;
    }

    public Observable<String> getTitleFromNetwork(String stringTitle) {
        Observable<OmdbAPI> movie = moviesExtraInfoApisService.getExtraInfoMovie(stringTitle);
        return movie.concatMap(new Function<OmdbAPI, Observable<String>>() {
            @Override
            public Observable<String> apply(OmdbAPI omdbAPI) {
                if (omdbAPI == null || omdbAPI.getPoster() == null) {
                    return Observable.just("No title");
                } else {
                    return Observable.just(omdbAPI.getTitle());
                }
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String title) {
                movieTitle = title;
            }
        });
    }

    public Observable<String> getCountryDataByTitle(String titleMovie) {
        Observable<OmdbAPI> movie = moviesExtraInfoApisService.getExtraInfoMovie(titleMovie);
        return movie.concatMap(new Function<OmdbAPI, Observable<String>>() {
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
                movieCountry = country;
            }
        });
    }

    public Observable<String> getImageDataByTitle(String titleMovie) {
        Observable<OmdbAPI> movie = moviesExtraInfoApisService.getExtraInfoMovie(titleMovie);
        return movie.concatMap(new Function<OmdbAPI, Observable<String>>() {
            @Override
            public Observable<String> apply(OmdbAPI omdbAPI) {
                return Observable.just(omdbAPI.getPoster());
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String image) {
                movieImage = image;
            }
        });
    }

    public Observable<ViewModel> getMyShit(String titleMovie) {
        return Observable.zip(getTitleFromNetwork(titleMovie), getCountryDataByTitle(titleMovie), getImageDataByTitle(titleMovie), new Function3<String, String, String, ViewModel>() {
            @Override
            public ViewModel apply(String title, String country, String image) {
                return new ViewModel(title, country, image);
            }
        });
    }
}
