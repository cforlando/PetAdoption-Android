package com.codefororlando.petadoption.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codefororlando.petadoption.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class PetImageViewPagerAdapter extends PagerAdapter {

    private final Context context;
    private final List<String> images;
    private final int placeholderImage;
    private final LayoutInflater mLayoutInflater;

    public PetImageViewPagerAdapter(Context context, List<String> images, int placeholderImage) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.images = images;
        this.placeholderImage = placeholderImage;
    }

    @Override
    public int getCount() {
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
        Picasso.with(context)
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
