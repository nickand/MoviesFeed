package com.nickand.moviesfeed.movies.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.airbnb.lottie.LottieAnimationView;
import com.nickand.moviesfeed.R;
import com.nickand.moviesfeed.di.App;
import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.movies.adapters.ListAdapter;
import com.nickand.moviesfeed.movies.mvp.MoviesMVP;
import com.nickand.moviesfeed.util.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements MoviesMVP.View {

    private static final String TAG = HomeFragment.class.getName();

    @BindView(R.id.rootView)
    ViewGroup rootView;

    @BindView(R.id.recyclerViewMovies)
    RecyclerView recyclerView;

    @BindView(R.id.placeSnackBar)
    ViewGroup placeSnackBar;

    @BindView(R.id.animation_view)
    LottieAnimationView lottieAnimationView;

    @Inject
    MoviesMVP.Presenter presenter;

    private ListAdapter listAdapter;
    private List<ViewModel> resultList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) Objects.requireNonNull(getActivity()).getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        listAdapter = new ListAdapter(resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.rxUnsubscribe();
        resultList.clear();
        listAdapter.notifyDataSetChanged();
        lottieAnimationView.clearAnimation();
    }

    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
        listAdapter.setImages(resultList);
        listAdapter.notifyItemInserted(resultList.size() - 1);
        Log.d(TAG, "New info: " + viewModel.getData());
        Log.d(TAG, "IMAGE: " + viewModel.getUrlImage());
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(placeSnackBar, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            lottieAnimationView.setAlpha(0f);
            lottieAnimationView.setVisibility(View.VISIBLE);

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            lottieAnimationView.animate()
                .alpha(1f)
                .setDuration(800);
            lottieAnimationView.setAnimation("json/movie_loader_coco.json");
            lottieAnimationView.setRepeatCount(Animation.INFINITE);
            lottieAnimationView.playAnimation();
        } else {
            // Animate the loading view to 0% opacity. After the animation ends,
            // set its visibility to GONE as an optimization step (it won't
            // participate in layout passes, etc.)
            lottieAnimationView.animate()
                .alpha(0f)
                .setDuration(800)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        lottieAnimationView.setVisibility(View.GONE);
                    }
                });

        }
    }
}
