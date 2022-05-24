package com.oze.footballfixtures.presentation.di.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oze.footballfixtures.presentation.DomainEntities

@Database(entities = [DomainEntities.DomainCompetitions::class, DomainEntities.DomainSeason::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun competitionsDao(): CompetitionsDao

    companion object {
        const val DATABASE_NAME = "competitions_database"
    }
}
