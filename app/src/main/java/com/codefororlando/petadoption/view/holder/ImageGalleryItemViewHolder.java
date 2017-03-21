package com.codefororlando.petadoption.view.holder;

import android.support.annotation.DrawableRes;
import android.view.View;

import com.codefororlando.petadoption.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;

public class ImageGalleryItemViewHolder {

    private final PhotoView photoView;

    public ImageGalleryItemViewHolder(View view) {
        photoView = (PhotoView) view.findViewById(R.id.photo_view);
    }

    public void bind(String image,
                     @DrawableRes int placeholderImageResource) {

        Picasso.with(photoView.getContext())
                .load(image)
                .placeholder(placeholderImageResource)
                .error(placeholderImageResource)
                .into(photoView);
    }

}
