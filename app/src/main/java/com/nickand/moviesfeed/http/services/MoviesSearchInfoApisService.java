package com.nickand.moviesfeed.http.services;

import com.nickand.moviesfeed.http.apimodel.OmdbAPI;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesSearchInfoApisService {

    @GET("/")
    Observable<OmdbAPI> getExtraInfoMovie(@Query("s") String title);
}
