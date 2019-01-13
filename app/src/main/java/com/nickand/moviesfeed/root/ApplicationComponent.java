package com.nickand.moviesfeed.root;

import com.nickand.moviesfeed.MainActivity;
import com.nickand.moviesfeed.fragments.FavoriteFragment;
import com.nickand.moviesfeed.fragments.HomeFragment;
import com.nickand.moviesfeed.fragments.SearchFragment;
import com.nickand.moviesfeed.http.MovieExtraInfoApiModule;
import com.nickand.moviesfeed.http.MovieTitleApiModule;
import com.nickand.moviesfeed.movies.MoviesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MoviesModule.class,
    MovieTitleApiModule.class, MovieExtraInfoApiModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    void inject(HomeFragment homeFragment);
    void inject(SearchFragment searchFragment);
    void inject(FavoriteFragment favoriteFragment);
}
