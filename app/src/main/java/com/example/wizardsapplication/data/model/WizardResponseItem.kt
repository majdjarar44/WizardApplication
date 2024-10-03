package com.example.wizardsapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.wizardsapplication.common.Converters

@Entity(tableName = "wizard_entity")
data class WizardResponseItem(

    @TypeConverters(
        Converters::class
    )
    var elixirs: List<Elixir>? = null,

    var firstName: String? = null,

    @PrimaryKey
    var id: String,

    var lastName: String? = null,

    var isFavorite: Boolean? = false
)

@Entity("favorite_entity")
data class Favorite(@PrimaryKey val id: String)