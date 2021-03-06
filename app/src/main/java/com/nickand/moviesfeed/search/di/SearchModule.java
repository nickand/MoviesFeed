package com.nickand.moviesfeed.search.di;

import com.nickand.moviesfeed.http.services.MoviesSearchInfoApisService;
import com.nickand.moviesfeed.search.mvp.SearchMVP;
import com.nickand.moviesfeed.search.mvp.model.SearchModel;
import com.nickand.moviesfeed.search.mvp.presenter.SearchPresenter;
import com.nickand.moviesfeed.search.repository.SearchRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    public SearchMVP.Presenter provideSearchPresenter(SearchMVP.Model searchModel) {
        return new SearchPresenter(searchModel);
    }

    @Provides
    public SearchMVP.Model provideSearchModel(SearchRepository repository) {
        return new SearchModel(repository);
    }

    @Provides
    @Singleton
    public SearchRepository provideSearchRepository(MoviesSearchInfoApisService moviesSearchInfoApisService) {
        return new SearchRepository(moviesSearchInfoApisService);
    }
}
