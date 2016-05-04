package com.codefororlando.petadoption.data;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public interface IAnimal extends IRetrievable {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({MALE, FEMALE})
    @interface Gender {}
    String MALE = "Male";
    String FEMALE = "Female";

    @NonNull
    String getId();

    @NonNull
    String getName();

    @NonNull
    String getSpecies();

    @NonNull
    String getBreed();

    @NonNull
    @Gender String getGender();

    @NonNull
    String getAge();

    boolean isAdoptable();

    boolean shouldActQuickly();

    @NonNull
    String getColor();

    @NonNull
    String getDescription();

    @NonNull
    String getActivityLevel();

    @NonNull
    String getIntakeDate();

    @NonNull
    String getShelterId();

    @NonNull
    List<String> getImages();
}
