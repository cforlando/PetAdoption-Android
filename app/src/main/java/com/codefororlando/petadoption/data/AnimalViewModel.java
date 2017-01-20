package com.codefororlando.petadoption.data;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;

import java.util.List;

/**
 * Created by johnli on 11/13/16.
 */

public class AnimalViewModel {

    private final Animal animal;

    public AnimalViewModel(Animal animal) {
        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }

    public String getDefaultImageUrl() {
        List<String> imageLinks = animal.getImages();
        if(imageLinks != null && imageLinks.size() > 0) {
            return imageLinks.get(0);
        } else {
            return null;
        }
    }

    /**
     * Get the animal placeholder image resource.
     *
     * @return placeholder image resource for the species or a dog for unknown species
     */
    @DrawableRes
    public int placeholderImageResource() {
        switch (animal.getSpecies()) {
            case "cat":
                return R.drawable.placeholder_cat;
            case "dog":
            default:
                return R.drawable.placeholder_dog;
        }
    }
}
