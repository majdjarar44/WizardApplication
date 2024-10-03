package com.example.wizardsapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wizardsapplication.data.model.Favorite
import com.example.wizardsapplication.data.model.WizardResponseItem
import com.example.wizardsapplication.common.Converters

@Database(
    version = 5,
    entities = [WizardResponseItem::class, Favorite::class],
    exportSchema = false
)

@TypeConverters(
    Converters::class
)
abstract class WizardItemDatabase : RoomDatabase() {
    abstract fun wizardItemDao(): WizardDao
}