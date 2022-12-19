package com.example.whatch_moovium;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>{
    private ArrayList<WatchlistModel> watchlistGallery;
    private Context context;
    private final Contract.WatchlistPresenter watchlistPresenter;

    public WatchlistAdapter(Context context, Contract.WatchlistPresenter watchlistPresenter, List<Movie> movieList){
        this.context = context;
        this.watchlistPresenter = watchlistPresenter;
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

        Model item = watchlistPresenter.getmovieList().get(position);



        holder.posterImgView.setImageResource(watchlistPresenter.getMovieList().get(position).getPoster());
    }

    @Override
    public int getItemCount() {
        return watchlistGallery.size();
    }


    public class WatchlistViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImgView;

        public WatchlistViewHolder(View view){
            super(view);

            posterImgView = itemView.findViewById(R.id.watchlist_item);


            /*posterImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    watchlistPresenter.onButtonClick();

                }
            });*/
        }
    }
}
