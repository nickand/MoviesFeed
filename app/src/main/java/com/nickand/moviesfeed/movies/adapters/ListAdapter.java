package com.nickand.moviesfeed.movies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.nickand.moviesfeed.R;
import com.nickand.moviesfeed.model.ViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<ViewModel> list;
    private List<ViewModel> url;

    public ListAdapter(List<ViewModel> list) {
        this.list = list;
    }

    public void setImages(List<ViewModel> url) {
        this.url = url;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.movie_list_item, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int i) {
        listItemViewHolder.itemName.setText(list.get(i).getData());
        listItemViewHolder.countryName.setText(list.get(i).getCountry());
        Glide.with(listItemViewHolder.itemImage.getContext())
            .load(url != null ? url.get(i).getUrlImage() : "")
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(new RequestOptions()
            .error(R.drawable.ic_error))
            .into(listItemViewHolder.itemImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieTitleTexView)
        public TextView itemName;
        @BindView(R.id.movieLocationTexView)
        public TextView countryName;
        @BindView(R.id.movieImageView)
        public ImageView itemImage;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
