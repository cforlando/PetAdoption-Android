package com.codefororlando.petadoption.data;

import java.util.List;

public interface IAnimalProvider {

    interface AnimalHandler {
        void onResult(boolean isSuccessful, List<? extends IAnimal> result);
    }

    void getAnimals(AnimalHandler handler);

    List<String> getQualifiedImagePaths(IAnimal animal);

}
