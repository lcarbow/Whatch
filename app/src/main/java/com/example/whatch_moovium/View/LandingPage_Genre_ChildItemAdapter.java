package com.example.whatch_moovium.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LandingPage_Genre_ChildItemAdapter extends RecyclerView.Adapter<LandingPage_Genre_ChildItemAdapter.ChildViewHolder> {

    private final Contract.IGenrePresenter genrePresenter;
    private List<Movie> ChildItemList;

    LandingPage_Genre_ChildItemAdapter(Contract.IGenrePresenter genrePresenter, List<Movie> childItemList)
    {
        this.genrePresenter = genrePresenter;
        this.ChildItemList = childItemList;

    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_poster, viewGroup, false);

        return new ChildViewHolder(view, genrePresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {

        Movie childItem = ChildItemList.get(position);
        String imgPath = childItem.getPoster();

        genrePresenter.setImageViewForLoader(childViewHolder.childImageView);
        genrePresenter.LoadImagesFromImageLoader(imgPath);
    }



    @Override
    public int getItemCount()
    {
        return ChildItemList.size();
    }



    class ChildViewHolder extends RecyclerView.ViewHolder {

        ImageView childImageView;

        ChildViewHolder(View itemView, Contract.IGenrePresenter genrePresenter) {
            super(itemView);

            childImageView = itemView.findViewById(R.id.img_child_item);

            childImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    genrePresenter.onClickImage(view, getAdapterPosition());

                }
            });
        }
    }
}

