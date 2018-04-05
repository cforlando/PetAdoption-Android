package com.codefororlando.petadoption.persistence.dao

import android.arch.persistence.room.*
import com.codefororlando.petadoption.persistence.model.AnimalEntity

@Dao
interface AnimalDao {

    @Query("SELECT * FROM animal")
    fun getAll(): List<AnimalEntity>

    @Query("SELECT * FROM animal WHERE id IN (:animalIds)")
    fun loadAllByIds(animalIds: IntArray): List<AnimalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(animals: List<AnimalEntity>)

    @Delete
    fun delete(animal: AnimalEntity)
}