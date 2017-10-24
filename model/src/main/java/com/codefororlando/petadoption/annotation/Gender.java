package com.codefororlando.petadoption.annotation;

import android.support.annotation.StringDef;

import com.codefororlando.petadoption.model.AnimalGender;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Define com.codefororlando.petadoption.annotation for genders
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({AnimalGender.MALE, AnimalGender.FEMALE})
public @interface Gender {
}
