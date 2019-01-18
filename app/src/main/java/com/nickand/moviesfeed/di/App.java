package com.nickand.moviesfeed.di;

import android.app.Application;

import com.nickand.moviesfeed.R;
import com.nickand.moviesfeed.http.di.MovieExtraInfoApiModule;
import com.nickand.moviesfeed.http.di.MovieSearchInfoApiModule;
import com.nickand.moviesfeed.http.di.MovieTitleApiModule;
import com.nickand.moviesfeed.movies.di.MoviesModule;
import com.nickand.moviesfeed.search.di.SearchModule;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        ViewPump.init(ViewPump.builder()
            .addInterceptor(new CalligraphyInterceptor(
                new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/BrownExtraBoldRegular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()))
            .build());

        component = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .moviesModule(new MoviesModule())
            .movieTitleApiModule(new MovieTitleApiModule())
            .movieExtraInfoApiModule(new MovieExtraInfoApiModule())
            .searchModule(new SearchModule())
            .movieSearchInfoApiModule(new MovieSearchInfoApiModule())
            .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
