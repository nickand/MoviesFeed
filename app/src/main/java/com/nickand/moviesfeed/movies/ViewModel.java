package com.nickand.moviesfeed.movies;

public class ViewModel {
    private String title;
    private String country;
    private String urlImage;

    public ViewModel(String data, String country, String urlImage) {
        title = data;
        this.country = country;
        this.urlImage = urlImage;
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
