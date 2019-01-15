package com.nickand.moviesfeed.root;

import com.nickand.moviesfeed.MainActivity;
import com.nickand.moviesfeed.favorites.view.FavoriteFragment;
import com.nickand.moviesfeed.movies.view.HomeFragment;
import com.nickand.moviesfeed.search.module.SearchModule;
import com.nickand.moviesfeed.search.view.SearchFragment;
import com.nickand.moviesfeed.http.module.MovieExtraInfoApiModule;
import com.nickand.moviesfeed.http.module.MovieTitleApiModule;
import com.nickand.moviesfeed.movies.module.MoviesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    MoviesModule.class,
    MovieTitleApiModule.class,
    MovieExtraInfoApiModule.class,
    SearchModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    void inject(HomeFragment homeFragment);
    void inject(SearchFragment searchFragment);
    void inject(FavoriteFragment favoriteFragment);
}
