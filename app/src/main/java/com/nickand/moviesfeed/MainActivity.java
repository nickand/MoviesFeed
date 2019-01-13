package com.nickand.moviesfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.nickand.moviesfeed.fragments.FavoriteFragment;
import com.nickand.moviesfeed.fragments.HomeFragment;
import com.nickand.moviesfeed.fragments.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getName();

    @BindView(R.id.fragmentContainer)
    ViewGroup fragmentContainer;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            loadFragment(homeFragment);
                            break;

                        case R.id.action_search:
                            Fragment searchFragment = new SearchFragment();
                            loadFragment(searchFragment);
                            break;

                        case R.id.action_favorites:
                            Fragment favoriteFragment = new FavoriteFragment();
                            loadFragment(favoriteFragment);
                            break;
                    }
                    return true;
                }
            });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
