package com.habz.cgv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LinkMovie extends RecyclerView.Adapter<LinkMovie.MovieViewHolder> {

    private List<ModelMovie> modelMovies;
    private int rowLayout;
    private Context context;


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView backbg;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            backbg = (ImageView) v.findViewById(R.id.backbg);
        }
    }

    public LinkMovie(List<ModelMovie> modelMovies, int rowLayout, Context context) {
        this.modelMovies = modelMovies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public LinkMovie.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(modelMovies.get(position).getTitle());
        holder.data.setText(modelMovies.get(position).getReleaseDate());
        holder.movieDescription.setText(modelMovies.get(position).getOverview());
        holder.rating.setText(modelMovies.get(position).getVoteAverage().toString());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + modelMovies.get(position).getBackdropPath()).resize(200, 250).into(holder.backbg);
    }

    @Override
    public int getItemCount() {
        return modelMovies.size();
    }
}
