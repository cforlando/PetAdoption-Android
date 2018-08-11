package com.codefororlando.petadoption.data.provider.petfinder

import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.data.provider.IAnimalProvider
import com.codefororlando.petadoption.data.provider.consumer.UpdateAnimalEntityConsumer
import com.codefororlando.petadoption.helper.IPreferencesHelper
import com.codefororlando.petadoption.network.IPetfinderService
import com.codefororlando.petadoption.network.model.pet.PetfinderAnimal
import com.codefororlando.petadoption.network.model.pet.PetfinderPetRecordResponse

import java.util.concurrent.TimeUnit

import javax.inject.Inject

import io.reactivex.Observable


class PetfinderProvider @Inject constructor(private val petfinderService: IPetfinderService,
                                            private val preferencesHelper: IPreferencesHelper,
                                            private val updateAnimalEntityConsumer: UpdateAnimalEntityConsumer) : IAnimalProvider {

    override fun getAnimals(count: Int, offset: String): Observable<List<Animal>> {
        return petfinderService.getAnimals(preferencesHelper.location, count, offset)
                .doOnError { throwable -> throwable.printStackTrace() }
                .map { petfinderPetRecordResponse -> toAnimalList(petfinderPetRecordResponse) }
                .doOnNext(updateAnimalEntityConsumer)
                .replay(5, TimeUnit.MINUTES)
                .autoConnect()
    }

    private fun toAnimalList(response: PetfinderPetRecordResponse): List<Animal> {
      return response.petfinder.pets.petList.map { animal ->
        Animal.AnimalBuilder()
            .setAge(animal.age.content)
            .setBreed(getBreed(animal.breeds.breed))
            .setDescription(animal.description.content)
            .setGender(animal.gender.content)
            .setId(animal.id.content)
            .setImages(photosToStringUrls(animal.media.photos))
            .setName(animal.name.content)
            .setShelterId(animal.shelterId.content)
            .setSpecies(animal.species.content)
            .createAnimal()
      }
    }

    private fun photosToStringUrls(photos: PetfinderAnimal.Media.Photos?): List<String> {
      return photos?.let { photos ->
        photos.photoList
            .filter { it.size == "x" }
            .map { it.url }
      } ?: emptyList()
    }

    private fun getBreed(breed: PetfinderAnimal.Breeds.Breed?): String {
      return breed?.let { breed.content } ?: ""
    }
}
