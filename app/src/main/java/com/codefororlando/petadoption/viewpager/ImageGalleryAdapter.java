package com.codefororlando.petadoption.viewpager;

import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.view.holder.ImageGalleryItemViewHolder;

import java.util.List;

public class ImageGalleryAdapter extends PagerAdapter {

    @DrawableRes
    private final int placeholderImageResource;
    private final List<String> images;

    public ImageGalleryAdapter(Animal animal) {
        this.placeholderImageResource = Animal.placeholderImageResource(animal);
        this.images = animal.getImages();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String image = images.get(position);
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        View view = layoutInflater.inflate(R.layout.photo_view, container, false);
        ImageGalleryItemViewHolder imageGalleryItemViewHolder = new ImageGalleryItemViewHolder(view);
        imageGalleryItemViewHolder.bind(image, placeholderImageResource);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
