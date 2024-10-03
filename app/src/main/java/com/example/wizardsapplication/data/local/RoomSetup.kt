package com.example.wizardsapplication.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


object DatabaseProvider {
    @Volatile
    private var INSTANCE: WizardItemDatabase? = null

    fun getDatabase(context: Context): WizardItemDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                WizardItemDatabase::class.java,
                "wizard_item"
            )
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)// This allows for destructive migrations
                .build()

            INSTANCE = instance
            instance
        }
    }
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE users "
                    + "ADD COLUMN address TEXT"
        )
    }
}
val MIGRATION_2_3: Migration = object : Migration(5,6 ) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add your migration SQL statements here
        database.execSQL(
            "ALTER TABLE wizard_entity ADD COLUMN new_column_name TEXT" // Update this as per your schema changes
        )
    }
}