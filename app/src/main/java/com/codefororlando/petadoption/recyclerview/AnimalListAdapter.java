package com.codefororlando.petadoption.recyclerview;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
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


    private OnAnimalSelectListener onAnimalSelectListener;

    @Override
    public AnimalItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.animal_item, parent, false);
        return new AnimalItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnimalItemViewHolder holder, int position) {
        final Animal animal = getAnimal(position);
        holder.setAnimal(animal);
        holder.itemView.setOnClickListener(v -> {
            if(onAnimalSelectListener != null) {
                onAnimalSelectListener.onSelect(animal);
            }
        });
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

    @Override
    public void setOnItemClickListener(OnAnimalSelectListener selectListener) {
        onAnimalSelectListener = selectListener;
    }

    private Animal getAnimal(@IntRange int position) {
        return animals.get(position);
    }

    private void clearAnimals() {
        animals.clear();
    }

    public void addAnimals(List<Animal> animals) {
        this.animals.addAll(animals);
    }
}
