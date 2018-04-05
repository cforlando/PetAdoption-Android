package com.codefororlando.petadoption.persistence.mapper

import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.persistence.model.AnimalEntity

/**
 * Created by john on 3/3/18.
 */

class AnimalMapper {

    fun map(from : Animal) : AnimalEntity {

     var animalEntity = AnimalEntity()
        animalEntity.id = from.id
        animalEntity.name = from.name
        animalEntity.species = from.species
        animalEntity.breed = from.breed
        animalEntity.gender = from.gender
        animalEntity.age = from.age
        animalEntity.adoptable = from.adoptable
        animalEntity.shouldActQuickly = from.shouldActQuickly
        animalEntity.color = from.color
        animalEntity.description = from.description
        animalEntity.activityLevel = from.activityLevel
        animalEntity.intakeDate = from.intakeDate
        animalEntity.shelterId = from.shelterId
//        animalEntity.images = from.images
        return animalEntity
    }

    fun map(from : List<Animal>) : List<AnimalEntity> {
        var to = ArrayList<AnimalEntity>()
        from.forEach {
            to.add(map(it))
        }
        return to
    }
}