package com.example.whatch_moovium.Presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.whatch_moovium.Contract;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class ImageLoader implements Contract.IImageLoader{

    private Context context;
    Picasso.Builder builder;
    ImageView imageView;

    public ImageLoader(Context context) {
        this.imageView = imageView;
        this.context = context;
        builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        //builder.indicatorsEnabled(true);
    }



    @Override
    public void loadImages(String imgPath) {
        builder.build().load("https://image.tmdb.org/t/p/w780" + imgPath)
                .into(imageView);
    }

    @Override
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
