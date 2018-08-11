package com.codefororlando.petadoption.persistence.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "animal_image",
        foreignKeys = arrayOf(ForeignKey(entity = AnimalEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("animalId"),
                onDelete = ForeignKey.CASCADE)))
data class AnimalImageEntity(@PrimaryKey(autoGenerate = true) val id: Int,
                             val animalId: String,
                             val url: String)
