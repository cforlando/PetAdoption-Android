package com.codefororlando.petadoption.data.provider.petfinder;

import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.provider.IAnimalProvider;
import com.codefororlando.petadoption.helper.IPreferencesHelper;
import com.codefororlando.petadoption.network.IPetfinderService;
import com.codefororlando.petadoption.network.model.pet.PetfinderAnimal;
import com.codefororlando.petadoption.network.model.pet.PetfinderPetRecordResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by ryan on 10/26/17.
 */

public class PetfinderProvider implements IAnimalProvider {

    private final IPetfinderService petfinderService;

    private final IPreferencesHelper preferencesHelper;

    @Inject
    public PetfinderProvider(IPetfinderService petfinderService, IPreferencesHelper preferencesHelper) {
        this.petfinderService = petfinderService;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public Observable<List<Animal>> getAnimals() {
        return petfinderService.getAnimals(preferencesHelper.getLocation())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                })
                .map(new Function<PetfinderPetRecordResponse, List<Animal>>() {

                    @Override
                    public List<Animal> apply(@NonNull PetfinderPetRecordResponse petfinderPetRecordResponse) throws Exception {
                        return toAnimalList(petfinderPetRecordResponse);
                    }
                })
                .replay(5, TimeUnit.MINUTES)
                .autoConnect();
    }


    private List<Animal> toAnimalList(PetfinderPetRecordResponse response) {
        final List<PetfinderAnimal> petfinderAnimals = response.petfinder.pets.petList;
        List<Animal> outputAnimals = new ArrayList<>();

        try {
            for (PetfinderAnimal animal : petfinderAnimals) {
                outputAnimals.add(new Animal.AnimalBuilder()
                        .setAge(animal.age.content)
                        .setBreed(getBreed(animal.breeds.breed))
                        .setDescription(animal.description.content)
                        .setGender(animal.gender.content)
                        .setId(animal.id.content)
                        .setImages(photosToStringUrls(animal.media.photos))
                        .setName(animal.name.content)
                        .setShelterId(animal.shelterId.content)
                        .setSpecies(animal.species.content)
                        .createAnimal());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return outputAnimals;
    }

    private List<String> photosToStringUrls(PetfinderAnimal.Media.Photos photos) {
        List<String> urlList = new ArrayList<>();

        if(photos != null){
            for (PetfinderAnimal.Media.Photos.Photo photo : photos.photoList) {
                if (photo.size.equals("x"))
                    urlList.add(photo.url);
            }
        }

        return urlList;
    }

    private String getBreed(PetfinderAnimal.Breeds.Breed breed) {
        String breedString = "";

        if(breed != null){
            breedString = breed.content;
        }

        return breedString;
    }
}
