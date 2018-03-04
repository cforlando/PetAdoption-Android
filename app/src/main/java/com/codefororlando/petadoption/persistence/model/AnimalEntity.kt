package com.codefororlando.petadoption.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by john on 3/3/18.
 */
@Entity
class AnimalEntity {

    @PrimaryKey
    internal var id: String? = null
    internal var name: String? = null
    internal var species: String? = null
    internal var breed: String? = null
    internal var gender: String? = null
    internal var age: String? = null
    internal var adoptable: Boolean = false
    @ColumnInfo(name = "act_quickly")
    internal var shouldActQuickly: Boolean = false
    internal var color: String? = null
    internal var description: String? = null
    @ColumnInfo(name = "activity_level")
    internal var activityLevel: String? = null
    internal var intakeDate: String? = null
    internal var shelterId: String? = null
    internal var images: List<String>? = null
}
