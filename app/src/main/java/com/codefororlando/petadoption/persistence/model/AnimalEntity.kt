package com.codefororlando.petadoption.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "animal")
data class AnimalEntity(@PrimaryKey @NonNull val id: String,
                        val name: String,
                        val species: String,
                        val breed: String,
                        val gender: String,
                        val age: String,
                        val adoptable: Boolean,
                        @ColumnInfo(name = "act_quickly") val shouldActQuickly: Boolean,
                        val color: String,
                        val description: String,
                        val activityLevel: String,
                        val intakeDate: String,
                        val shelterId: String)
