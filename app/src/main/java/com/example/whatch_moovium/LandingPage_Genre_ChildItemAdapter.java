package com.example.whatch_moovium;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class LandingPage_Genre_ChildItemAdapter extends RecyclerView.Adapter<LandingPage_Genre_ChildItemAdapter.ChildViewHolder> {

    private List<Movie> ChildItemList;
    //Presenter

    LandingPage_Genre_ChildItemAdapter(List<Movie> childItemList)
    {
        this.ChildItemList = childItemList;//
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_poster, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {

        Movie childItem = ChildItemList.get(position);
        childViewHolder.childImageView.setImageBitmap(childItem.getPosterBitmap());
    }



    @Override
    public int getItemCount()
    {
        return ChildItemList.size();
    }



    class ChildViewHolder extends RecyclerView.ViewHolder {

        ImageView childImageView;

        ChildViewHolder(View itemView) {
            super(itemView);

            childImageView = itemView.findViewById(R.id.img_child_item);

            childImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(),MovieSuggestion.class);
                    view.getContext().startActivity(i);
                    Log.i("userdebug","Umleitung zur MovieSugg");
                }
            });
        }
    }
}

