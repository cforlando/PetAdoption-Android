package com.codefororlando.petadoption.data.impl;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.IAnimalProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StaticAnimalProvider implements IAnimalProvider {

    private static StaticAnimalProvider instance;

    private final List<IAnimal> animals;

    public StaticAnimalProvider(@NonNull Context context) {
        IAnimal[] animalArray = {
                new Animal(getUriForDrawable(context, R.drawable.puppy_1)),
                new Animal(getUriForDrawable(context, R.drawable.puppy_2)),
                new Animal(getUriForDrawable(context, R.drawable.puppy_3)),
                new Animal(getUriForDrawable(context, R.drawable.puppy_4)),
                new Animal(getUriForDrawable(context, R.drawable.puppy_5)),
                new Animal(getUriForDrawable(context, R.drawable.puppy_6)),
                new Animal(getUriForDrawable(context, R.drawable.puppy_7)),
                new Animal(getUriForDrawable(context, R.drawable.puppy_8)),
        };

        animals = Collections.unmodifiableList(Arrays.asList(animalArray));
    }

    public static StaticAnimalProvider getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (StaticAnimalProvider.class) {
                if (instance == null) {
                    instance = new StaticAnimalProvider(context);
                }
            }
        }

        return instance;
    }

    private static Uri getUriForDrawable(@NonNull Context context,
                                         @DrawableRes int drawable) {

        return Uri.parse("android.resource://" + context.getPackageName() + "/" + drawable);
    }

    @Override
    public List<IAnimal> getAnimals() {
        return animals;
    }

}
