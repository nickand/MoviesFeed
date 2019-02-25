package com.nickand.moviesfeed.favorites.mvp.presenter;

import com.nickand.moviesfeed.favorites.mvp.FavoriteMVP;
import com.nickand.moviesfeed.favorites.mvp.FavoriteManager;
import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.movies.mvp.MoviesMVP;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FavoritesPresenter implements FavoriteMVP.Presenter {

    private FavoriteMVP.View view;
    private FavoriteMVP.Model model;
    private FavoriteManager favoriteManager;

    private Disposable subscription = null;

    public FavoritesPresenter(FavoriteMVP.Model model, FavoriteManager favoriteManager) {
        this.model = model;
        this.favoriteManager = favoriteManager;
    }

    @Override
    public void loadData() {
        view.showProgress(true);
    }

    @Override
    public void rxUnsubscribe() {

    }

    @Override
    public void setAsFavorite(ViewModel viewModel) {
        favoriteManager.saveFavorite(viewModel);

    }

    @Override
    public void setView(FavoriteMVP.View view) {
        this.view = view;
    }
}
