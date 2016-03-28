package com.codefororlando.petadoption.data;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public interface IAnimal extends IRetrievable {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({MALE, FEMALE})
    public @interface Gender {}
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    @NonNull
    String getName();

    @NonNull
    String getSpecies();

    @NonNull
    String getBreed();

    @NonNull
    @Gender String getGender();

    List<? extends IRetrievable> getImages();
}
