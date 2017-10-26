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

    public Animal(AnimalBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.species = builder.species;
        this.breed = builder.breed;
        this.gender = builder.gender;
        this.age = builder.age;
        this.adoptable = builder.adoptable;
        this.shouldActQuickly = builder.shouldActQuickly;
        this.color = builder.color;
        this.description = builder.description;
        this.activityLevel = builder.activityLevel;
        this.intakeDate = builder.intakeDate;
        this.shelterId = builder.shelterId;
        this.images = builder.images;
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

    public static class AnimalBuilder {
        String id;
        String name;
        String species;
        String breed;
        String gender;
        String age;
        Boolean adoptable = true;
        Boolean shouldActQuickly = true;
        String color = "";
        String description;
        String activityLevel = "";
        String intakeDate = "";
        String shelterId;
        List<String> images;

        public AnimalBuilder() {
        }

        public AnimalBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public AnimalBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AnimalBuilder setSpecies(String species) {
            this.species = species;
            return this;
        }

        public AnimalBuilder setBreed(String breed) {
            this.breed = breed;
            return this;
        }

        public AnimalBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public AnimalBuilder setAge(String age) {
            this.age = age;
            return this;
        }

        public AnimalBuilder setAdoptable(Boolean adoptable) {
            this.adoptable = adoptable;
            return this;
        }

        public AnimalBuilder setShouldActQuickly(Boolean shouldActQuickly) {
            this.shouldActQuickly = shouldActQuickly;
            return this;
        }

        public AnimalBuilder setColor(String color) {
            this.color = color;
            return this;
        }

        public AnimalBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public AnimalBuilder setActivityLevel(String activityLevel) {
            this.activityLevel = activityLevel;
            return this;
        }

        public AnimalBuilder setIntakeDate(String intakeDate) {
            this.intakeDate = intakeDate;
            return this;
        }

        public AnimalBuilder setShelterId(String shelterId) {
            this.shelterId = shelterId;
            return this;
        }

        public AnimalBuilder setImages(List<String> images) {
            this.images = images;
            return this;
        }

        public Animal createAnimal() {
            return new Animal(this);
        }

    }
}
