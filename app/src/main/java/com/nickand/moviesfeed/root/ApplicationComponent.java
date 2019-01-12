package com.nickand.moviesfeed.root;

import com.nickand.moviesfeed.MainActivity;
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
}
