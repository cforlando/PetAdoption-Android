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

    @JsonField(name = "petId")
    String id;
    @JsonField(name = "petName")
    String name;
    @JsonField
    String species;
    @JsonField
    String breed;
    @Gender
    @JsonField
    String gender;
    @JsonField
    String age;
    @JsonField(name = "adoptable")
    Boolean adoptable;
    @JsonField(name = "shouldActQuickly")
    Boolean shouldActQuickly;
    @JsonField
    String color;
    @JsonField
    String description;
    @JsonField
    String activityLevel;
    @JsonField
    String intakeDate;
    @JsonField
    String shelterId;
    @JsonField
    List<String> images;

    Animal() {
        // Empty constructor for logansquare
    }

    public Animal(Parcel in) {
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

    public List<String> getImages() {
        return images;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public Boolean getAdoptable() {
        return adoptable;
    }

    public Boolean getShouldActQuickly() {
        return shouldActQuickly;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public String getIntakeDate() {
        return intakeDate;
    }

    public String getShelterId() {
        return shelterId;
    }

}
