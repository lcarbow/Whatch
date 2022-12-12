package com.example.whatch_moovium;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatch_moovium.Model.Movie;

import java.util.ArrayList;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewholder{
    private Context context;
    private ArrayList<Movie> watchlistGallery;

    //TODO replace IntegerList with Movielist
    public WatchlistAdapter(Context context, ArrayList<Movie> watchlistGallery){
        this.context = context;
        this.watchlistGallery = watchlistGallery;

    }

    @NonNull
    @Override
    public WatchlistAdapter.WatchlistViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.watchlist_page_row, parent, false);
        return new WatchlistAdapter.WatchlistViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistAdapter.WatchlistViewholder holder, int position) {
        holder.posterImg.setImageResource(watchlistGallery.get(position).getPoster());
    }

    @Override
    public int getItemCount() {
        return watchlistGallery.size();
    }

    public class WatchlistViewholder extends RecyclerView.ViewHolder{
        private ImageView posterImg;
        public AdapterViewHolder(View view){
            super(view);

            posterImgView = (ImageView) view.findViewById(R.id.watchlist_item);
        }
    }
}
