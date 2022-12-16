package com.example.whatch_moovium;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>{
    private ArrayList<WatchlistModel> watchlistGallery;
    private Context context;

    public WatchlistAdapter(Context context, ArrayList<WatchlistModel> watchlistGallery){
        this.context = context;
        this.watchlistGallery = watchlistGallery;
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.watchlist_page_row, viewGroup, false);
        return new WatchlistAdapter.WatchlistViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
        holder.posterImgView.setImageResource(watchlistGallery.get(position).getPoster());
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
