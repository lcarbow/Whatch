package com.example.whatch_moovium;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class LandingPage_Genre_ChildItemAdapter
        extends RecyclerView
        .Adapter<LandingPage_Genre_ChildItemAdapter.ChildViewHolder> {

    private List<Movie> ChildItemList;

    // Constructor
    LandingPage_Genre_ChildItemAdapter(List<Movie> childItemList)
    {
        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.genre_poster,
                        viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChildViewHolder childViewHolder,
            int position)
    {

        // Create an instance of the ChildItem
        // class for the given position
        Movie childItem = ChildItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        /*
        childViewHolder
                .ChildItemTitle
                .setText(childItem.getChildItemTitle());

         */
        //childViewHolder.childImageView.setImageBitmap(childItem.getPoster());
    }



    @Override
    public int getItemCount()
    {

        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return ChildItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ChildViewHolder
            extends RecyclerView.ViewHolder {

        //TextView ChildItemTitle;
        ImageView childImageView;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            childImageView = itemView.findViewById(R.id.img_child_item);
            /*
            ChildItemTitle
                    = itemView.findViewById(
                    R.id.child_item_title);

             */
        }
    }
}

