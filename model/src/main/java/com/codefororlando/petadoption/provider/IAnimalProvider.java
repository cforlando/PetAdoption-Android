package com.codefororlando.petadoption.provider;

import com.codefororlando.petadoption.model.Animal;

import java.util.List;

import io.reactivex.Observable;

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
