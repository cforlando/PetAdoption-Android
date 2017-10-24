package com.codefororlando.petadoption.provider;

import com.codefororlando.petadoption.model.Shelter;

import io.reactivex.Observable;

/**
 * Created by johnli on 11/13/16.
 */

public interface IShelterProvider {

    Observable<Shelter> getShelter(String id);
}
