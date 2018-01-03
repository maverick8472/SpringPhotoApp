package com.code_evolve_8472.springphotoapp;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code_evolve_8472.springphotoapp.Helper.Upload;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder>{

    private List<Upload> images;
    private Context context;

    public GalleryAdapter(){}

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
    public GalleryAdapter(Context context, List<Upload> images) {
        this.context = context;
        this.images = images;
    }
    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_adapter,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.MyViewHolder holder, int position) {
        Upload image = images.get(position);

        Glide.with(context).load(image.getUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

    }
    @Override
    public int getItemCount() {
        return images.size();
    }

}
