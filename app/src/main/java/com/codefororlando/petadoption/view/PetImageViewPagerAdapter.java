package com.codefororlando.petadoption.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codefororlando.petadoption.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class PetImageViewPagerAdapter extends PagerAdapter {

    private List<String> images;
    private @DrawableRes int placeholderImage;
    private final LayoutInflater mLayoutInflater;

    public PetImageViewPagerAdapter(final Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setImages(List<String> images, @DrawableRes int placeholderImage) {
        this.images = images;
        this.placeholderImage = placeholderImage;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(images == null) {
            return 0;
        }
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_content);
        Picasso.with(container.getContext())
                .load(images.get(position))
                .resize(1000, 1000)
                .onlyScaleDown()
                .centerCrop()
                .placeholder(placeholderImage)
                .into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
