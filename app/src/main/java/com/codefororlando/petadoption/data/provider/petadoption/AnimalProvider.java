package com.codefororlando.petadoption.data.provider.petadoption;

import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.provider.IAnimalProvider;
import com.codefororlando.petadoption.network.IPetAdoptionService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;

/**
 * Created by tencent on 10/10/16.
 */
public class AnimalProvider implements IAnimalProvider {

    private final IPetAdoptionService petAdoptionService;

    @Inject
    public AnimalProvider(IPetAdoptionService petAdoptionService) {
        this.petAdoptionService = petAdoptionService;
    }

    @Override
    public Observable<List<Animal>> getAnimals(int count, String offset) {
        return petAdoptionService.getAnimals()
                .replay(5, TimeUnit.MINUTES)
                .autoConnect();
    }

}
