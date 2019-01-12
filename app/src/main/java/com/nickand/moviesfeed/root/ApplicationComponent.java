package com.nickand.moviesfeed.root;

import com.nickand.moviesfeed.MainActivity;
import com.nickand.moviesfeed.http.MovieExtraInfoApiModule;
import com.nickand.moviesfeed.http.MovieTitleApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MovieTitleApiModule.class, MovieExtraInfoApiModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
