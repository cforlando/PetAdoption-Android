package com.codefororlando.petadoption.data.annotation;

import android.support.annotation.StringDef;

import com.codefororlando.petadoption.data.model.AnimalGender;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Define annotation for genders
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({AnimalGender.MALE, AnimalGender.FEMALE})
public @interface Gender {
}
