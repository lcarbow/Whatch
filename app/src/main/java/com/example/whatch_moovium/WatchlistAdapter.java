package com.example.whatch_moovium;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.whatch_moovium.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>{
    //private ArrayList<WatchlistModel> watchlistGallery;
    private List<Movie> watchlistGallery;
    private final Contract.WatchlistPresenter watchlistPresenter;
    private Context context;

    public WatchlistAdapter(Contract.WatchlistPresenter watchlistPresenter, Context context, List<Movie> watchlistGallery){
        this.watchlistPresenter = watchlistPresenter;
        this.context = context;
        this.watchlistGallery = watchlistGallery;
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.watchlist_page_row, viewGroup, false);
        return new WatchlistAdapter.WatchlistViewHolder(view, watchlistPresenter);

    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
       //holder.posterImgView.setImageResource(watchlistGallery.get(position).getPoster());
        Movie movieItem = watchlistGallery.get(position);
        String imgPath = movieItem.getPoster();

        watchlistPresenter.setImageViewForLoader(WatchlistViewHolder.posterImgView);
        watchlistPresenter.LoadImagesFromImageLoader(imgPath);
    }

    @Override
    public int getItemCount() {
        return watchlistGallery.size();
    }


    static class WatchlistViewHolder extends RecyclerView.ViewHolder {
        static ImageView posterImgView;

        public WatchlistViewHolder(View view, Contract.WatchlistPresenter watchlistPresenter){
            super(view);

            posterImgView = itemView.findViewById(R.id.watchlist_item);


            posterImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    watchlistPresenter.onButtonClick();

                }
            });
        }
    }
}
