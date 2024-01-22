package com.ltdd.nhakhoaapp.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.domain.Image;


import java.util.List;

public class ImageAdapter extends PagerAdapter {

    private Context context;
    private List<Image> images;


    public ImageAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
    }
    @Override
    public int getCount() {
        return (images == null) ? 0:images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_slidebar, container, false);
        ImageView img = view.findViewById(R.id.imgPhoto);

        Image image = images.get(position);
        if(image != null) {
            Glide.with(context).load(image.getResourceId()).into(img);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
