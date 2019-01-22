package com.nickand.moviesfeed.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "movies")
public class ViewModel {
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    @NonNull
    private String id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "image")
    private String urlImage;

    @Ignore
    public ViewModel(String data, String country, String urlImage) {
        title = data;
        this.country = country;
        this.urlImage = urlImage;
    }

    public ViewModel(String id, String title, String country, String urlImage) {
        this.id = id;
        this.title = title;
        this.country = country;
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
