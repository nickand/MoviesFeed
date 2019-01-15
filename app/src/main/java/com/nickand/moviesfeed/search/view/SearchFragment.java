package com.nickand.moviesfeed.search.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.nickand.moviesfeed.R;
import com.nickand.moviesfeed.model.ViewModel;
import com.nickand.moviesfeed.root.App;
import com.nickand.moviesfeed.search.adapters.SearchListAdapter;
import com.nickand.moviesfeed.search.mvp.SearchMVP;
import com.nickand.moviesfeed.util.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class SearchFragment extends Fragment implements SearchMVP.View{

    private static final String TAG = SearchFragment.class.getName();

    @BindView(R.id.rootView)
    ViewGroup rootView;

    @BindView(R.id.recyclerViewMovies)
    RecyclerView recyclerView;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @BindView(R.id.placeSnackBar)
    ViewGroup placeSnackBar;

    @Inject
    SearchMVP.Presenter presenter;

    private SearchListAdapter listAdapter;
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        listAdapter = new SearchListAdapter(resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    private void performSearch() {
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView text, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (resultList != null && listAdapter != null) {
                        resultList.clear();
                        listAdapter.notifyDataSetChanged();
                    }

                    presenter.loadDataByTitle(text.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        performSearch();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.rxUnsubscribe();
    }

    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
        listAdapter.setImages(resultList);
        listAdapter.notifyItemInserted(resultList.size() - 1);
    }

    @Override
    public void showSnackbar(String message) {

    }
}
