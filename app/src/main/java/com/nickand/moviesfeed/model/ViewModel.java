package com.nickand.moviesfeed.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "movies")
public class ViewModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    @NonNull
    private int id;
    @ColumnInfo(name = "title")
    private String data;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "image")
    private String urlImage;

    public ViewModel(String data, String country, String urlImage) {
        this.data = data;
        this.country = country;
        this.urlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
