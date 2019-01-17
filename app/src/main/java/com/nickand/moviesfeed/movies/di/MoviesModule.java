package com.nickand.moviesfeed.movies.di;

import com.nickand.moviesfeed.http.services.MoviesApiService;
import com.nickand.moviesfeed.http.services.MoviesExtraInfoApisService;
import com.nickand.moviesfeed.movies.mvp.MoviesMVP;
import com.nickand.moviesfeed.movies.mvp.model.MoviesModel;
import com.nickand.moviesfeed.movies.mvp.presenter.MoviesPresenter;
import com.nickand.moviesfeed.movies.repository.MoviesRepository;
import com.nickand.moviesfeed.repository.Repository;

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
