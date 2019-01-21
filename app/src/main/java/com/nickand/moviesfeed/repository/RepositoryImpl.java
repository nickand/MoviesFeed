package com.nickand.moviesfeed.repository;

import com.nickand.moviesfeed.http.apimodel.ResultDataInformation;
import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.room.MovieDao;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;

public abstract class RepositoryImpl<T extends ResultDataInformation> implements Repository, MovieDao {

    @Override
    public Observable<ViewModel> getDataResult() {
        return Observable.zip(getDataByTitle(), getImageDataByTitle(), getMovieCountryData(), new Function3<T, String, String, ViewModel>() {
            @Override
            public ViewModel apply(T resultData, String image, String country) {
                return new ViewModel(resultData.getTitle(), country, image);
            }
        });
    }

    @Override
    public Observable<ViewModel> getDataResult(String searchData) {
        return Observable.zip(getDataByTitle(searchData), getImageDataByTitle(searchData),
                new BiFunction<T, String, ViewModel>() {
                    @Override
                    public ViewModel apply(T resultDataInfo, String image) {
                        return new ViewModel(resultDataInfo.getTitle(), "No country", image);
                    }
                });
    }

    @Override
    public void save(ViewModel viewModel) {

    }

    @Override
    public void delete(ViewModel viewModel) {

    }

    @Override
    public void deleteAllMovies() {

    }

    @Override
    public Observable<ViewModel> load(int movieId) {
        return null;
    }

    @Override
    public Observable<List<ViewModel>> getAllMovies() {
        return null;
    }

    protected Observable<T> getDataByTitle() {
        return null;
    }

    protected Observable<T> getDataByTitle(String searchData) {
        return null;
    }

    protected Observable<String> getImageDataByTitle() {
        return null;
    }

    protected Observable<String> getImageDataByTitle(String searchData) {
        return null;
    }

    public abstract Observable<String> getMovieCountryData();
}
