package com.codefororlando.petadoption.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.codefororlando.petadoption.persistence.model.AnimalEntity


/**
 * Created by john on 3/3/18.
 */

@Dao
interface AnimalDao {

    @Query("SELECT * FROM animal")
    fun getAll(): List<AnimalEntity>

    @Query("SELECT * FROM animal WHERE id IN (:animalIds)")
    fun loadAllByIds(animalIds: IntArray): List<AnimalEntity>

    @Insert
    fun insertAll(animals: List<AnimalEntity>)

    @Delete
    fun delete(animal: AnimalEntity)
}