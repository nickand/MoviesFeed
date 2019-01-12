package com.nickand.moviesfeed.http;

import com.nickand.moviesfeed.http.apimodel.TopMoviesRated;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("top_rated")
    Observable<TopMoviesRated> getTopMoviesRated(
        @Query("api_key") String apiKey, @Query("page") Integer page);
}
