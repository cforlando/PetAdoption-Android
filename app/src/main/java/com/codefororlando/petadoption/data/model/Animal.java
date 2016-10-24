package com.codefororlando.petadoption.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.annotation.Gender;

import java.util.List;

@SuppressWarnings("WeakerAccess")
@JsonObject
public class Animal implements Parcelable {

    protected Animal(Parcel in) {
        id = in.readString();
        name = in.readString();
        species = in.readString();
        breed = in.readString();

        // Annotation type casting
        @Gender
        String readGender = in.readString();
        gender = readGender;

        age = in.readString();
        color = in.readString();
        description = in.readString();
        activityLevel = in.readString();
        intakeDate = in.readString();
        shelterId = in.readString();
        images = in.createStringArrayList();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(species);
        parcel.writeString(breed);
        parcel.writeString(gender);
        parcel.writeString(age);
        parcel.writeString(color);
        parcel.writeString(description);
        parcel.writeString(activityLevel);
        parcel.writeString(intakeDate);
        parcel.writeString(shelterId);
        parcel.writeStringList(images);
    }

}
