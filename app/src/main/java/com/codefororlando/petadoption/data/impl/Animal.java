package com.codefororlando.petadoption.data.impl;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.IRetrievable;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IAnimal {

    // Properties

    @NonNull
    private final Uri uri;

    @NonNull
    private String name;

    @NonNull
    private
    @Gender
    String gender;

    @NonNull
    private String species;

    @NonNull
    String breed;

    List<RetrievableImpl> images;

    // Constructors

    public Animal(@NonNull Uri uri, @NonNull String name, @NonNull @Gender String gender,
                  @NonNull String species, @NonNull String breed) {
        this.uri = uri;
        this.name = name;
        this.gender = gender;
        this.species = species;
        this.breed = breed;
        images = new ArrayList<>();
    }

    public Animal(@NonNull Uri uri) {
        this.uri = uri;
    }

    protected Animal(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    // IRetrievable Implementation
    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    @NonNull
    public Uri getUri() {
        return uri;
    }

    @Override
    @NonNull
    public String getTag() {
        return uri.toString();
    }

    // IAnimal Implementation

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String getSpecies() {
        return species;
    }

    @NonNull
    @Override
    public String getBreed() {
        return breed;
    }

    @NonNull
    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public List<? extends IRetrievable> getImages() {
        return images;
    }

    public void setImages(List<RetrievableImpl> images) {
        this.images = images;
    }
}
