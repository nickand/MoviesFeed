package com.nickand.moviesfeed.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.nickand.moviesfeed.room.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context providerContext() {
        return application;
    }

    @Provides
    @Singleton
    public MovieDatabase provideMovieDatabase() {
        return Room.databaseBuilder(providerContext(),
            MovieDatabase.class, "moviesfeed.db")
            .build();
    }
}
