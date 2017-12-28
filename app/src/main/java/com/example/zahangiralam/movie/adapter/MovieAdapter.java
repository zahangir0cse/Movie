package com.example.zahangiralam.movie.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zahangiralam.movie.MainActivity;
import com.example.zahangiralam.movie.R;
import com.example.zahangiralam.movie.dao.DatabaseHelper;
import com.example.zahangiralam.movie.listener.ItemClickListener;
import com.example.zahangiralam.movie.model.Movie;

import java.util.List;

/**
 * Created by Zahangir Alam on 2017-12-27.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>{


    private Context mCtx;
    private List<Movie> movieList;

    public MovieAdapter(Context mCtx, List<Movie> movieList) {
        this.mCtx = mCtx;
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.movie_layout, null);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        final Movie movie = movieList.get(position);
        holder.textViewName.setText(movie.getMovieName());
        holder.textViewShortDesc.setText(movie.getMovieDescription());
        holder.textViewRating.setText(String.valueOf(movie.getMovieRatting()));
        holder.textViewGenre.setText(String.valueOf(movie.getMovieGenre()));
        holder.imageView.setImageBitmap(movie.getMovieImage());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Delete Alert");
                builder.setMessage("Do you want to delete this movie item?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper helper = new DatabaseHelper(mCtx);
                        helper.deleteMovie(movie);
                        Toast.makeText(mCtx, "Movie deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mCtx, "You chose not to delete it", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

}
