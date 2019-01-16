package com.nickand.moviesfeed.http.services;

import com.nickand.moviesfeed.http.apimodel.OmdbApiSearch;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesSearchInfoApisService {

    @GET("/")
    Observable<OmdbApiSearch> getExtraInfoMovie(@Query("s") String title);
}
