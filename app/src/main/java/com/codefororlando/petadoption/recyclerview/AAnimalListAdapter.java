package com.codefororlando.petadoption.recyclerview;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codefororlando.petadoption.data.model.Animal;

import java.util.List;

/**
 * Created by tencent on 10/10/16.
 */
public abstract class AAnimalListAdapter<VH extends RecyclerView.ViewHolder> extends
        RecyclerView.Adapter<VH> {

    @MainThread
    public abstract void setAnimals(@NonNull List<Animal> animals);

    public abstract void setOnItemClickListener(OnAnimalSelectListener selectListener);

    public interface OnAnimalSelectListener {
        void onSelect(Animal animal);
    }
}
