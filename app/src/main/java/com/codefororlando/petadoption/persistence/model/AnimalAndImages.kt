package com.codefororlando.petadoption.persistence.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

/** NOTE-JLI
    Had to separate the default constructor and images field due to Room's constructor requirements and how Kotlin
    generates constructors for data classes
    https://stackoverflow.com/questions/46956109/room-related-entities-usable-public-constructor
 */
data class AnimalAndImages(@Embedded
                           var animal: AnimalEntity) {

    @Relation(parentColumn = "id", entityColumn = "animalId", entity = AnimalImageEntity::class, projection = ["url"])
    lateinit var images: List<String>
}
