package com.nickand.moviesfeed.di;

import com.nickand.moviesfeed.MainActivity;
import com.nickand.moviesfeed.favorites.view.FavoriteFragment;
import com.nickand.moviesfeed.http.di.MovieExtraInfoApiModule;
import com.nickand.moviesfeed.http.di.MovieSearchInfoApiModule;
import com.nickand.moviesfeed.http.di.MovieTitleApiModule;
import com.nickand.moviesfeed.movies.di.MoviesModule;
import com.nickand.moviesfeed.movies.view.HomeFragment;
import com.nickand.moviesfeed.search.di.SearchModule;
import com.nickand.moviesfeed.search.view.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    MoviesModule.class,
    MovieTitleApiModule.class,
    MovieExtraInfoApiModule.class,
    SearchModule.class,
    MovieSearchInfoApiModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    void inject(HomeFragment homeFragment);
    void inject(SearchFragment searchFragment);
    void inject(FavoriteFragment favoriteFragment);
}
