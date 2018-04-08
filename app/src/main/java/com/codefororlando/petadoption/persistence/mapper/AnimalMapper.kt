package com.codefororlando.petadoption.persistence.mapper

import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.persistence.model.AnimalEntity
import javax.inject.Inject


class AnimalMapper @Inject constructor(){

    fun map(from: Animal): AnimalEntity {
        return AnimalEntity(from.id,
                from.name,
                from.species,
                from.breed,
                from.gender,
                from.age,
                from.adoptable,
                from.shouldActQuickly,
                from.color,
                from.description,
                from.activityLevel,
                from.intakeDate,
                from.shelterId)
    }

    fun map(from: List<Animal>): List<AnimalEntity> {
        var to = ArrayList<AnimalEntity>()
        from.forEach {
            to.add(map(it))
        }
        return to
    }
}