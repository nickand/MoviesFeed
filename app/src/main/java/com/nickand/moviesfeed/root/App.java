package com.nickand.moviesfeed.root;

import android.app.Application;

import com.nickand.moviesfeed.http.MovieExtraInfoApiModule;
import com.nickand.moviesfeed.http.MovieTitleApiModule;
import com.nickand.moviesfeed.movies.MoviesModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .moviesModule(new MoviesModule())
            .movieTitleApiModule(new MovieTitleApiModule())
            .movieExtraInfoApiModule(new MovieExtraInfoApiModule())
            .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
