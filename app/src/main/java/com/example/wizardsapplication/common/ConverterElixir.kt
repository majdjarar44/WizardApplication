package com.example.wizardsapplication.common

import androidx.room.TypeConverter
import com.example.wizardsapplication.data.model.Elixir

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    @TypeConverter
    fun fromElixirList(value: List<Elixir>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toElixirList(value: String?): List<Elixir>? {
        val listType = object : TypeToken<List<Elixir>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
