package com.codefororlando.petadoption.persistence

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.codefororlando.petadoption.persistence.dao.AnimalAndImagesDao
import com.codefororlando.petadoption.persistence.dao.AnimalDao
import com.codefororlando.petadoption.persistence.dao.AnimalImageDao
import com.codefororlando.petadoption.persistence.model.AnimalEntity
import com.codefororlando.petadoption.persistence.model.AnimalImageEntity


@Database(entities = arrayOf(AnimalEntity::class, AnimalImageEntity::class), version = 2, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao
    abstract fun animalImageDao(): AnimalImageDao
    abstract fun animalAndImagesDao(): AnimalAndImagesDao
}
