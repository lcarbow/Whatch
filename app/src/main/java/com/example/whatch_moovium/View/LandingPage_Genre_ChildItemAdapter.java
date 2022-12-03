package com.example.whatch_moovium.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatch_moovium.Model.Movie;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.R;

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
        childViewHolder.childtextView.setText(childItem.getTitle());
    }



    @Override
    public int getItemCount()
    {
        return ChildItemList.size();
    }



    class ChildViewHolder extends RecyclerView.ViewHolder {

        ImageView childImageView;
        TextView childtextView;

        ChildViewHolder(View itemView) {
            super(itemView);

            childImageView = itemView.findViewById(R.id.img_child_item);
            childtextView = itemView.findViewById(R.id.testView);

            childImageView.setOnClickListener(new View.OnClickListener() {
                ///////////////AUSLAGERN IN PRESENTER TODO ///////////////
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), MovieSuggestion.class);
                    view.getContext().startActivity(i);
                    StorageClass.getInstance().getMyModel().setIndex(getAdapterPosition());


                }
            });
            ////////////////////////////////////
        }
    }
}

