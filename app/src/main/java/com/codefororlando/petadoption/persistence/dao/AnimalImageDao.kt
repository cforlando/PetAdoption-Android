package com.codefororlando.petadoption.persistence.dao

import android.arch.persistence.room.*
import com.codefororlando.petadoption.persistence.model.AnimalImageEntity

@Dao
interface AnimalImageDao {

    @Update
    fun update(vararg images: AnimalImageEntity)

    @Delete
    fun delete(vararg images: AnimalImageEntity)

    @Insert
    fun insert(image: AnimalImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(animalImages: List<AnimalImageEntity>)

    @Query("SELECT * FROM animal_image")
    fun getAll(): List<AnimalImageEntity>

    @Query("SELECT * FROM animal_image WHERE animalId=:animalId")
    fun findImagesForAnimal(animalId: Int): List<AnimalImageEntity>
}