package com.nickand.moviesfeed.movies.mvp.presenter;

import com.nickand.moviesfeed.movies.mvp.MoviesMVP;
import com.nickand.moviesfeed.model.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviesPresenter implements MoviesMVP.Presenter {

    private MoviesMVP.View view;
    private MoviesMVP.Model model;

    private Disposable subscription = null;

    public MoviesPresenter(MoviesMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData() {
        view.showProgress(true);

        subscription = model.result()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    throwable.printStackTrace();
                }
            })
            .subscribeWith(new DisposableObserver<ViewModel>() {
                @Override
                public void onNext(ViewModel viewModel) {
                    if (view != null) {
                        view.showProgress(false);
                        view.updateData(viewModel);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    if (view != null) {
                        view.showProgress(false);
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
    public void setView(MoviesMVP.View view) {
        this.view = view;
    }
}
