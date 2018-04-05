package com.codefororlando.petadoption.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "animal")
class AnimalEntity {

    @PrimaryKey @NonNull
     var id: String? = null
     var name: String? = null
     var species: String? = null
     var breed: String? = null
     var gender: String? = null
     var age: String? = null
     var adoptable: Boolean = false
    @ColumnInfo(name = "act_quickly")
     var shouldActQuickly: Boolean = false
     var color: String? = null
     var description: String? = null
    @ColumnInfo(name = "activity_level")
     var activityLevel: String? = null
     var intakeDate: String? = null
     var shelterId: String? = null
//     var images: List<String>? = null
}
