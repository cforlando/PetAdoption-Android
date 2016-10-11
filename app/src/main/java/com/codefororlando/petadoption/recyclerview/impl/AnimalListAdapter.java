package com.codefororlando.petadoption.recyclerview.impl;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.recyclerview.AAnimalListAdapter;
import com.codefororlando.petadoption.view.AnimalItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tencent on 10/10/16.
 */
public class AnimalListAdapter extends AAnimalListAdapter<AnimalItemViewHolder> {

    private final List<Animal> animals;

    public AnimalListAdapter() {
        animals = new ArrayList<>();
    }

    @Override
    public AnimalItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.animal_item, parent, false);
        return new AnimalItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnimalItemViewHolder holder, int position) {
        Animal animal = getAnimal(position);
        holder.setAnimal(animal);
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    @Override
    public void setAnimals(@NonNull List<Animal> animals) {
        clearAnimals();
        addAnimals(animals);
    }

    private Animal getAnimal(@IntRange int position) {
        return animals.get(position);
    }

    private void clearAnimals() {
        animals.clear();
    }

    private void addAnimals(List<Animal> animals) {
        this.animals.addAll(animals);
    }

}
