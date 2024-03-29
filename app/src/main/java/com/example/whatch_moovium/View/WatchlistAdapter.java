package com.example.whatch_moovium.View;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.R;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>{
    private List<Movie> watchlistGallery;
    private Context context;
    private final Contract.IWatchlistPresenter watchlistPresenter;

    public WatchlistAdapter(Context context, Contract.IWatchlistPresenter watchlistPresenter, List<Movie> movieList){
        this.context = context;
        this.watchlistPresenter = watchlistPresenter;
        this.watchlistGallery = movieList;
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.watchlist_page_row, viewGroup, false);
        return new WatchlistViewHolder(view, watchlistPresenter);

    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {

        Movie item = watchlistGallery.get(position);
        String imgPath = item.getPoster();
        watchlistPresenter.setImageViewForLoader(holder.posterImgView);
        watchlistPresenter.LoadImagesFromImageLoader(imgPath);

    }

    @Override
    public int getItemCount() {
        return watchlistGallery.size();
    }


    public class WatchlistViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImgView;

        public WatchlistViewHolder(View view, Contract.IWatchlistPresenter watchlistPresenter){
            super(view);

            posterImgView = itemView.findViewById(R.id.watchlist_item);

            posterImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    watchlistPresenter.onClickImage(view, getAdapterPosition());
                    watchlistPresenter.setMovie(getAdapterPosition());

                }
            });

        }
    }
}
