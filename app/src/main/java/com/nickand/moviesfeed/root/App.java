package com.nickand.moviesfeed.root;

import android.app.Application;

import com.nickand.moviesfeed.R;
import com.nickand.moviesfeed.http.module.MovieExtraInfoApiModule;
import com.nickand.moviesfeed.http.module.MovieTitleApiModule;
import com.nickand.moviesfeed.movies.module.MoviesModule;
import com.nickand.moviesfeed.search.module.SearchModule;

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
            .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
