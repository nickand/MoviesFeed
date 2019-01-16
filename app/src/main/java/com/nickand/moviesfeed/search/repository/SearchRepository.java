package com.nickand.moviesfeed.search.repository;

import com.nickand.moviesfeed.http.apimodel.OmdbApiSearch;
import com.nickand.moviesfeed.http.apimodel.Search;
import com.nickand.moviesfeed.http.services.MoviesSearchInfoApisService;
import com.nickand.moviesfeed.repository.RepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SearchRepository extends RepositoryImpl<Search> {

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

    private Observable<Search> getTitleFromNetwork(String stringTitle) {
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
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private Observable<String> getImageByTitle(String titleMovie) {
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

    @Override
    protected Observable<Search> getDataByTitle(String searchData) {
        return getTitleFromNetwork(searchData);
    }

    @Override
    protected Observable<String> getImageDataByTitle(String searchData) {
        return getImageByTitle(searchData);
    }

    @Override
    public Observable<String> getMovieCountryData() {
        return Observable.just("No Country");
    }
}
