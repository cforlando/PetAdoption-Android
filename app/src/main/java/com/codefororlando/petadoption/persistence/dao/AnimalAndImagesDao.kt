package com.codefororlando.petadoption.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.codefororlando.petadoption.persistence.model.AnimalAndImages


@Dao
interface AnimalAndImagesDao {
    @Query("SELECT * from animal")
    fun loadAll(): List<AnimalAndImages>
}