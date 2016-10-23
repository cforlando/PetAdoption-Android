package com.codefororlando.petadoption.data.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.annotation.Gender;

import java.util.List;

@JsonObject
public class Animal {

    /**
     * Get the animal placeholder image resource.
     *
     * @return placeholder image resource for the species or a dog for unknown species
     */
    @DrawableRes
    public static int placeholderImageResource(@NonNull Animal animal) {
        switch (animal.species) {
            case "cat":
                return R.drawable.placeholder_cat;
            case "dog":
            default:
                return R.drawable.placeholder_dog;
        }
    }

    @JsonField(name = "petId")
    public String id;

    @JsonField(name = "petName")
    public String name;

    @JsonField
    public String species;

    @JsonField
    public String breed;

    @Gender
    @JsonField
    public String gender;

    @JsonField
    public String age;

    @JsonField(name = "adoptable")
    public Boolean adoptable;

    @JsonField(name = "shouldActQuickly")
    public Boolean shouldActQuickly;

    @JsonField
    public String color;

    @JsonField
    public String description;

    @JsonField
    public String activityLevel;

    @JsonField
    public String intakeDate;

    @JsonField
    public String shelterId;

    @JsonField
    public List<String> images;

}
