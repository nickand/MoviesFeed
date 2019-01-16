package com.nickand.moviesfeed.repository;

import com.nickand.moviesfeed.model.ViewModel;

import io.reactivex.Observable;

public interface Repository {

    Observable<ViewModel> getDataResult();
    Observable<ViewModel> getDataResult(String searchData);
}
