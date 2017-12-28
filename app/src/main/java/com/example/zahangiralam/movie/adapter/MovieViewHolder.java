package com.example.zahangiralam.movie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zahangiralam.movie.R;
import com.example.zahangiralam.movie.listener.ItemClickListener;

/**
 * Created by Zahangir Alam on 2017-12-27.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
    TextView textViewName, textViewShortDesc, textViewRating, textViewGenre;
    ImageView imageView;
    ItemClickListener itemClickListener;

    public MovieViewHolder(View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.textViewName);
        textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
        textViewRating = itemView.findViewById(R.id.textViewRating);
        textViewGenre = itemView.findViewById(R.id.textViewGenre);
        imageView = itemView.findViewById(R.id.imageView);
        imageView.buildDrawingCache();
        imageView.getDrawingCache();
        itemView.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        this.itemClickListener.onItemClick(view, getLayoutPosition());
        return false;
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;


    }
}
