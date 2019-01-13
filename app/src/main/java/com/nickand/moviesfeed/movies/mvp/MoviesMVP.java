package com.nickand.moviesfeed.movies.mvp;

import com.nickand.moviesfeed.model.ViewModel;

import io.reactivex.Observable;

public interface MoviesMVP {

    interface View {
        void updateData(ViewModel viewModel);
        void showSnackbar(String message);
    }

    interface Presenter {
        void loadData();
        void rxUnsubscribe();
        void setView(MoviesMVP.View view);
    }

    interface Model {
        Observable<ViewModel> result();
    }
}
