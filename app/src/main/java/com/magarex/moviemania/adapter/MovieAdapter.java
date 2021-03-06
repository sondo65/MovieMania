package com.magarex.moviemania.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magarex.moviemania.databinding.MovieItemBinding;
import com.magarex.moviemania.ui.activities.DetailActivity;
import com.magarex.moviemania.interfaces.ItemClickListener;
import com.magarex.moviemania.models.Movie;
import com.magarex.moviemania.R;

import org.parceler.Parcels;

import java.util.List;

class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListener itemClickListener;
    final MovieItemBinding mBinding;

    MovieViewHolder(MovieItemBinding itemBinding) {
        super(itemBinding.getRoot());
        this.mBinding = itemBinding;
        mBinding.getRoot().setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final Activity mActivity;
    private List<Movie> mMovies;

    public MovieAdapter(Activity activity) {
        this.mActivity = activity;
    }

    public void addMoviesList(List<Movie> movieList) {
        this.mMovies = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_item, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        holder.mBinding.setMovie(mMovies.get(position));
        holder.setItemClickListener((view, position1, isLongClick) -> {
            Intent intent = new Intent(mActivity, DetailActivity.class);
            Bundle movieData = new Bundle();
            movieData.putParcelable("data", Parcels.wrap(mMovies.get(position)));
            intent.putExtra("moviePoster", mMovies.get(position).getPosterPath());
            intent.putExtras(movieData);

            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(holder.mBinding.imgMoviePoster, "posterTransition");
            pairs[1] = new Pair<View, String>(holder.mBinding.cvMovieItem, "posterCardViewTransition");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, pairs);
                mActivity.startActivity(intent, options.toBundle());
            }else {
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        } else {
            return mMovies.size();
        }
    }
}
