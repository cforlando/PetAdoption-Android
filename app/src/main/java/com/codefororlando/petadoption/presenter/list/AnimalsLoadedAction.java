package com.codefororlando.petadoption.presenter.list;

import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.recyclerview.AAnimalListAdapter;

import java.util.List;

import rx.functions.Action1;

class AnimalsLoadedAction implements Action1<List<Animal>> {

    private final AAnimalListAdapter animalListAdapter;

    AnimalsLoadedAction(AAnimalListAdapter animalListAdapter) {
        this.animalListAdapter = animalListAdapter;
    }

    @Override
    public void call(List<Animal> animals) {
        animalListAdapter.setAnimals(animals);
        animalListAdapter.notifyDataSetChanged();
    }

}
