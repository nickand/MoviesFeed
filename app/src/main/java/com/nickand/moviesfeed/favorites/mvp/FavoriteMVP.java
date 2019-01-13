package com.nickand.moviesfeed.favorites.mvp;

import com.nickand.moviesfeed.movies.mvp.model.ViewModel;

import io.reactivex.Observable;

public interface FavoriteMVP {

    interface View {
        void updateData(ViewModel viewModel);
        void showSnackbar(String message);
    }

    interface Presenter {
        void loadData();
        void rxUnsubscribe();
        void setView(FavoriteMVP.View view);
    }

    interface Model {
        Observable<ViewModel> result();
    }
}
