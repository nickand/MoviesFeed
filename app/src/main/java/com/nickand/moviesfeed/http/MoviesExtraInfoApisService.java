package com.nickand.moviesfeed.http;

import com.nickand.moviesfeed.http.apimodel.OmdbAPI;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesExtraInfoApisService {

    @GET("/")
    Observable<OmdbAPI> getExtraInfoMovie(@Query("t") String title);
}
