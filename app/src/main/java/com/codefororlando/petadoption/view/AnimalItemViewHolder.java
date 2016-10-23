package com.codefororlando.petadoption.view;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tencent on 10/10/16.
 */
public class AnimalItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageViewAnimal;
    private final Resources resources;

    public AnimalItemViewHolder(View itemView) {
        super(itemView);
        this.imageViewAnimal = (ImageView) itemView.findViewById(R.id.animal_image);
        this.resources = imageViewAnimal.getResources();
    }

    public void setAnimal(Animal animal) {
        @DrawableRes
        int placeholderImageResource = Animal.placeholderImageResource(animal);

        String contentDescription = resources.getString(R.string.animal_image_content_description,
                animal.species,
                animal.name);

        imageViewAnimal.setContentDescription(contentDescription);

        List<String> images = animal.images;
        if (images.size() > 0) {
            String image = images.get(0);
            Picasso.with(imageViewAnimal.getContext())
                    .load(image)
                    .placeholder(placeholderImageResource)
                    .error(placeholderImageResource)
                    .fit()
                    .centerCrop()
                    .into(imageViewAnimal);
        } else {
            imageViewAnimal.setImageResource(placeholderImageResource);
        }
    }

}
