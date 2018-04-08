package com.codefororlando.petadoption.data.provider.consumer

import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.persistence.dao.AnimalDao
import com.codefororlando.petadoption.persistence.dao.AnimalImageDao
import com.codefororlando.petadoption.persistence.mapper.AnimalMapper
import com.codefororlando.petadoption.persistence.model.AnimalImageEntity
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class UpdateAnimalEntityConsumer @Inject constructor(private val animalMapper:AnimalMapper,
                                                     private val animalDao: AnimalDao,
                                                     private val imageDao: AnimalImageDao) : Consumer<List<Animal>> {

    override fun accept(animalList: List<Animal>) {
        animalDao.insertAll(animalList.map(animalMapper::map).toMutableList())
        var images = mutableListOf<AnimalImageEntity>()
        animalList.forEach { animal ->
            animal.images.forEach { url ->
                var imageEntity = AnimalImageEntity(0, animal.id, url)  //id is ultimately auto-generated, any value is okay to pass in
                images.add(imageEntity)
            }
        }
        imageDao.insertAll(images)
        Observable.just(animalList)
    }

}