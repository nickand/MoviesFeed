package com.nickand.moviesfeed.http.di;

import com.nickand.moviesfeed.http.services.MoviesApiService;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MovieTitleApiModule {

    private final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final String API_KEY = "8bcaf9145962fc555e2957ec68a3dd59";


    @Provides
    public OkHttpClient provideClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", API_KEY).build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            }).build();
    }

    @Provides
    public Retrofit provideRetrofit(String BASE_URL, OkHttpClient client) {
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    public MoviesApiService provideApiService() {
        return provideRetrofit(BASE_URL, provideClient()).create(MoviesApiService.class);
    }
}
