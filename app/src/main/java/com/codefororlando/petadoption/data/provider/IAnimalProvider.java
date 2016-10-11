package com.codefororlando.petadoption.data.provider;

import com.codefororlando.petadoption.data.model.Animal;

import java.util.List;

import rx.Observable;

/**
 * Created by tencent on 10/10/16.
 */
public interface IAnimalProvider {

    /**
     * Get all animals.
     *
     * @return all animals
     */
    Observable<List<Animal>> getAnimals();

}
