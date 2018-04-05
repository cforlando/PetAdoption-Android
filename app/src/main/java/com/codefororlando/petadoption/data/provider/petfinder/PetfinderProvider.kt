package com.codefororlando.petadoption.data.provider.petfinder

import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.data.provider.IAnimalProvider
import com.codefororlando.petadoption.helper.IPreferencesHelper
import com.codefororlando.petadoption.network.IPetfinderService
import com.codefororlando.petadoption.network.model.pet.PetfinderAnimal
import com.codefororlando.petadoption.network.model.pet.PetfinderPetRecordResponse
import com.codefororlando.petadoption.persistence.dao.AnimalDao
import com.codefororlando.petadoption.persistence.mapper.AnimalMapper

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import javax.inject.Inject

import io.reactivex.Observable


class PetfinderProvider @Inject constructor(private val petfinderService: IPetfinderService,
                                            private val preferencesHelper: IPreferencesHelper,
                                            private val animalDao: AnimalDao) : IAnimalProvider {

    override fun getAnimals(count: Int, offset: String): Observable<List<Animal>> {
        return petfinderService.getAnimals(preferencesHelper.location, count, offset)
                .doOnError { throwable -> throwable.printStackTrace() }
                .map { petfinderPetRecordResponse -> toAnimalList(petfinderPetRecordResponse) }
                .flatMap<List<Animal>> {
                    val animalMapper = AnimalMapper()
                    animalDao.insertAll(it.map(animalMapper::map).toMutableList())
                    Observable.just(it)
                }
                .replay(5, TimeUnit.MINUTES)
                .autoConnect()
    }

    private fun toAnimalList(response: PetfinderPetRecordResponse): List<Animal> {
        val petfinderAnimals = response.petfinder.pets.petList
        val outputAnimals = ArrayList<Animal>()

        try {
            for (animal in petfinderAnimals) {
                outputAnimals.add(Animal.AnimalBuilder()
                        .setAge(animal.age.content)
                        .setBreed(getBreed(animal.breeds.breed))
                        .setDescription(animal.description.content)
                        .setGender(animal.gender.content)
                        .setId(animal.id.content)
                        .setImages(photosToStringUrls(animal.media.photos))
                        .setName(animal.name.content)
                        .setShelterId(animal.shelterId.content)
                        .setSpecies(animal.species.content)
                        .createAnimal())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return outputAnimals
    }

    private fun photosToStringUrls(photos: PetfinderAnimal.Media.Photos?): List<String> {
        val urlList = ArrayList<String>()

        if (photos != null) {
            for (photo in photos.photoList) {
                if (photo.size == "x")
                    urlList.add(photo.url)
            }
        }

        return urlList
    }

    private fun getBreed(breed: PetfinderAnimal.Breeds.Breed?): String {
        var breedString = ""

        if (breed != null) {
            breedString = breed.content
        }
        return breedString
    }
}
