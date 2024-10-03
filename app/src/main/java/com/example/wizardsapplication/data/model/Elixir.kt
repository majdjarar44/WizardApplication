package com.example.wizardsapplication.data.model

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Elixir(

    @ColumnInfo
    var id: String? = null,

    @ColumnInfo
    var name: String? = null
)