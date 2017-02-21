package com.codefororlando.petadoption.presenter.list;

import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.recyclerview.AAnimalListAdapter;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

class AnimalsLoadedAction implements Consumer<List<Animal>> {

    private final AAnimalListAdapter animalListAdapter;

    AnimalsLoadedAction(AAnimalListAdapter animalListAdapter) {
        this.animalListAdapter = animalListAdapter;
    }

    @Override
    public void accept(@NonNull List<Animal> animals) throws Exception {
        animalListAdapter.setAnimals(animals);
        animalListAdapter.notifyDataSetChanged();
    }

}
