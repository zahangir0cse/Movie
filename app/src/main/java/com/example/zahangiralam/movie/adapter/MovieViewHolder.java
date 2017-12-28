package com.example.zahangiralam.movie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zahangiralam.movie.R;

/**
 * Created by Zahangir Alam on 2017-12-27.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder{
    TextView textViewName, textViewShortDesc, textViewRating, textViewGenre;
    ImageView imageView;

    public MovieViewHolder(View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.textViewName);
        textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
        textViewRating = itemView.findViewById(R.id.textViewRating);
        textViewGenre = itemView.findViewById(R.id.textViewGenre);
        imageView = itemView.findViewById(R.id.imageView);
    }
}
