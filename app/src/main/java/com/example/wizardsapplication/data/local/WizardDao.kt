package com.example.wizardsapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wizardsapplication.data.model.Favorite
import com.example.wizardsapplication.data.model.WizardResponseItem

@Dao
interface WizardDao {

    @Query("SELECT * FROM wizard_entity")
    fun getAllWizards(): List<WizardResponseItem>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(wizards: List<WizardResponseItem>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(id: Favorite)

    @Query("SELECT * FROM favorite_entity")
    fun getAllFavoriteIDs(): List<Favorite>

    @Query("DELETE FROM favorite_entity WHERE id = :id")
    fun removeFavorite(id: String)
}