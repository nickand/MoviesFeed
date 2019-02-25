package com.nickand.moviesfeed.favorites.mvp;

import com.nickand.moviesfeed.model.ViewModel;

import io.reactivex.Observable;

public interface FavoriteMVP {

    interface View {
        void updateData(ViewModel viewModel);
        void showSnackbar(String message);
        void showFavorite(boolean isFavorite);
        void showProgress(boolean isShow);
    }

    interface Presenter {
        void loadData();
        void rxUnsubscribe();
        void setAsFavorite(ViewModel viewModel);
        void setView(FavoriteMVP.View view);
    }

    interface Model {
        Observable<ViewModel> result();
    }
}
