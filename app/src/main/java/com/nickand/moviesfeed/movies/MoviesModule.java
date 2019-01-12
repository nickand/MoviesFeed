package com.nickand.moviesfeed.movies;

import com.nickand.moviesfeed.http.MoviesApiService;
import com.nickand.moviesfeed.http.MoviesExtraInfoApisService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

    @Provides
    public MoviesMVP.Presenter provideMoviesPresenter(MoviesMVP.Model moviesModel) {
        return new MoviesPresenter(moviesModel);
    }

    @Provides
    public MoviesMVP.Model provideMovieModel(Repository repository) {
        return new MoviesModel(repository);
    }

    @Provides
    @Singleton
    public Repository provideMovieRepository(MoviesApiService moviesApiService,
                                             MoviesExtraInfoApisService moviesExtraInfoApisService) {
        return new MoviesRepository(moviesApiService, moviesExtraInfoApisService);
    }
}
