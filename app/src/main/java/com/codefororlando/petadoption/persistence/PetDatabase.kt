package com.codefororlando.petadoption.persistence

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.codefororlando.petadoption.persistence.dao.AnimalDao
import com.codefororlando.petadoption.persistence.model.AnimalEntity


@Database(entities = arrayOf(AnimalEntity::class), version = 1)
abstract class PetDatabase : RoomDatabase() {
    abstract fun animaliDao(): AnimalDao
}
