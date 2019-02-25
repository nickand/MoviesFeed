package com.nickand.moviesfeed.favorites.di;

import com.nickand.moviesfeed.favorites.mvp.FavoriteMVP;
import com.nickand.moviesfeed.favorites.mvp.FavoriteManager;
import com.nickand.moviesfeed.favorites.mvp.model.FavoritesModel;
import com.nickand.moviesfeed.favorites.mvp.presenter.FavoritesPresenter;
import com.nickand.moviesfeed.favorites.repository.FavoritesRepository;
import com.nickand.moviesfeed.http.services.MoviesApiService;
import com.nickand.moviesfeed.http.services.MoviesExtraInfoApisService;
import com.nickand.moviesfeed.movies.repository.MoviesRepository;
import com.nickand.moviesfeed.repository.Repository;
import com.nickand.moviesfeed.room.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesModule {

    @Provides
    public FavoriteMVP.Presenter provideMoviesPresenter(FavoriteMVP.Model favoritesModel, FavoriteManager favoriteManager) {
        return new FavoritesPresenter(favoritesModel, favoriteManager);
    }

    @Provides
    public FavoriteMVP.Model provideMovieModel(Repository repository) {
        return new FavoritesModel(repository);
    }

    @Provides
    public FavoriteManager provideFavoritesManager(MovieDatabase movieDatabase) {
        return new FavoriteManager(movieDatabase);
    }

    @Provides
    @Singleton
    public Repository provideMovieRepository(MoviesApiService moviesApiService,
                                             MoviesExtraInfoApisService moviesExtraInfoApisService) {
        return new MoviesRepository(moviesApiService, moviesExtraInfoApisService);
    }

    @Provides
    @Singleton
    public FavoritesRepository provideMovieDatabase(MovieDatabase database) {
        return new FavoritesRepository(database);
    }
}
