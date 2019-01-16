package com.nickand.moviesfeed.search.repository;

import com.nickand.moviesfeed.http.apimodel.OmdbApiSearch;
import com.nickand.moviesfeed.http.apimodel.Result;
import com.nickand.moviesfeed.http.apimodel.Search;
import com.nickand.moviesfeed.http.services.MoviesSearchInfoApisService;
import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SearchRepository implements Repository {

    private MoviesSearchInfoApisService moviesExtraInfoApisService;

    private String movieTitle;
    private String movieCountry;
    private String movieImage;
    private List<Search> searchList;
    private long lastTimestamp;

    private static final long CACHE_LIFETIME = 20 * 1000;

    public SearchRepository(MoviesSearchInfoApisService eService) {
        moviesExtraInfoApisService = eService;

        this.lastTimestamp = System.currentTimeMillis();

        movieTitle = "";
        movieCountry = "";
        movieImage = "";

        searchList = new ArrayList<>();
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

    public Observable<Search> getTitleFromNetwork(String stringTitle) {
        Observable<OmdbApiSearch> movie = moviesExtraInfoApisService.getExtraInfoMovie(stringTitle);
        return movie.concatMap(new Function<OmdbApiSearch, Observable<Search>>() {
            @Override
            public Observable<Search> apply(OmdbApiSearch omdbApiSearch) {
                return Observable.fromIterable(omdbApiSearch.getSearch());
            }
        }).concatMap(new Function<Search, Observable<Search>>() {
            @Override
            public Observable<Search> apply(Search search) {
                return Observable.just(search);
            }
        }).doOnNext(new Consumer<Search>() {
            @Override
            public void accept(Search search) {
                searchList.add(search);
            }
        });
    }

    public Observable<String> getImageDataByTitle(String titleMovie) {
        Observable<Search> movie = getTitleFromNetwork(titleMovie);
        return movie.concatMap(new Function<Search, Observable<String>>() {
            @Override
            public Observable<String> apply(Search search) {
                return Observable.just(search.getPoster());
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String image) {
                movieImage = image;
            }
        });
    }

    public Observable<ViewModel> getMyShit(String titleMovie) {
        return Observable.zip(getTitleFromNetwork(titleMovie), getImageDataByTitle(titleMovie),
            new BiFunction<Search, String, ViewModel>() {
                @Override
                public ViewModel apply(Search search, String image) {
                    return new ViewModel(search.getTitle(), "No country", image);
                }
            });
    }
}
