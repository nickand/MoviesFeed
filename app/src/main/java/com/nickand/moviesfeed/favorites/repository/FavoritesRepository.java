package com.nickand.moviesfeed.favorites.repository;

import com.nickand.moviesfeed.http.apimodel.OmdbAPI;
import com.nickand.moviesfeed.http.apimodel.Result;
import com.nickand.moviesfeed.http.apimodel.TopMoviesRated;
import com.nickand.moviesfeed.http.services.MoviesApiService;
import com.nickand.moviesfeed.http.services.MoviesExtraInfoApisService;
import com.nickand.moviesfeed.repository.RepositoryImpl;
import com.nickand.moviesfeed.room.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class FavoritesRepository extends RepositoryImpl<Result> {

    private List<String> countries;
    private List<String> images;
    private List<Result> results;
    private long lastTimestamp;

    private static final long CACHE_LIFETIME = 20 * 1000;

    public FavoritesRepository(MovieDatabase database) {

    }

    private boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }


    @Override
    public Observable<String> getMovieCountryData() {
        return null;
    }
}
