package com.nickand.moviesfeed.search.mvp.presenter;

import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.search.mvp.SearchMVP;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchMVP.Presenter {

    private SearchMVP.View view;
    private SearchMVP.Model model;

    private Disposable subscription = null;

    public SearchPresenter(SearchMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadDataByTitle(String title) {
        subscription = model.result(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<ViewModel>() {
                @Override
                public void onNext(ViewModel viewModel) {
                    if (view != null) {
                        view.updateData(viewModel);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    if (view != null) {
                        view.showSnackbar("Download movie error");
                    }
                }

                @Override
                public void onComplete() {
                    if (view != null) {
                        view.showSnackbar("Movies loaded");
                    }
                }
            });
    }

    @Override
    public void rxUnsubscribe() {
        if (subscription != null && subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    @Override
    public void setView(SearchMVP.View view) {
        this.view = view;
    }
}
