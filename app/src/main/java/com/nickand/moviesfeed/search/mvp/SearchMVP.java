package com.nickand.moviesfeed.search.mvp;

import com.nickand.moviesfeed.model.ViewModel;

import io.reactivex.Observable;

public interface SearchMVP {

    interface View {
        void updateData(ViewModel viewModel);
        void showSnackbar(String message);
    }

    interface Presenter {
        void loadDataByTitle(Observable<String> title);
        void rxUnsubscribe();
        void setView(SearchMVP.View view);
    }

    interface Model {
        Observable<ViewModel> result(Observable<String> titleMovie);
    }
}
