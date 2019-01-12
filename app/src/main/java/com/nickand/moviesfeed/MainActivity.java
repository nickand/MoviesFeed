package com.nickand.moviesfeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nickand.moviesfeed.movies.MoviesMVP;
import com.nickand.moviesfeed.movies.ViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesMVP.View {

    private final String TAG = MainActivity.class.getName();

    @BindView(R.id.activityRootView)
    ViewGroup rootView;

    @BindView(R.id.recyclerViewMovies)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    public void updateData(ViewModel viewModel) {

    }

    @Override
    public void showSnackbar(String message) {

    }
}
