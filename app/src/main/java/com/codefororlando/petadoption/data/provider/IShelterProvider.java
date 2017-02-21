package com.codefororlando.petadoption.data.provider;

import com.codefororlando.petadoption.data.model.Shelter;

import io.reactivex.Observable;

/**
 * Created by johnli on 11/13/16.
 */

public interface IShelterProvider {

    Observable<Shelter> getShelter(String id);
}
