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


public class PetfinderProvider implements IAnimalProvider {

    private final IPetfinderService petfinderService;

    private final IPreferencesHelper preferencesHelper;

    @Inject
    public PetfinderProvider(IPetfinderService petfinderService, IPreferencesHelper preferencesHelper) {
        this.petfinderService = petfinderService;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public Observable<List<Animal>> getAnimals(int count, String offset) {
        return petfinderService.getAnimals(preferencesHelper.getLocation(), count, offset)
                .doOnError(throwable -> throwable.printStackTrace())
                .map(petfinderPetRecordResponse -> toAnimalList(petfinderPetRecordResponse))
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
