package com.example.whatch_moovium;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/*
public class LandingPage_Genres_AdapterTitle extends RecyclerView.Adapter<LandingPage_Genres_AdapterTitle.MyParentViewHolder> {

    // An object of RecyclerView.RecycledViewPool is created to share the Views
    // between the child and the parent RecyclerViews

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<LandingPage_GenresModelTitle> genreTitleList;

    public LandingPage_Genres_AdapterTitle(ArrayList<LandingPage_GenresModelTitle> genreTitleList) {
        this.genreTitleList = genreTitleList;
    }

    public class MyParentViewHolder extends RecyclerView.ViewHolder{
        private TextView titleView;

        public MyParentViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.genreTitleView);
        }
    }

    @NonNull
    @Override
    public MyParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_titles, parent, false);
        return new MyParentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyParentViewHolder holder, int position) {
        String title = genreTitleList.get(position).getGenreTitle();
        holder.titleView.setText(title);
    }

    @Override
    public int getItemCount() {
        return genreTitleList.size();
    }


}*/
