package com.nickand.moviesfeed.http;

import com.nickand.moviesfeed.http.apimodel.Poster;
import com.nickand.moviesfeed.http.apimodel.TopMoviesRated;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("top_rated")
    Observable<TopMoviesRated> getTopMoviesRated(@Query("page") Integer page);
    @GET("{movie_id}/images")
    Observable<Poster> getImages(@Path("movie_id") int id);
}
