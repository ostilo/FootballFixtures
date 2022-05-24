package com.oze.footballfixtures.presentation.di.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oze.footballfixtures.presentation.DomainEntities

@Dao
interface CompetitionsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCompetitions(competitions: List<DomainEntities.DomainCompetitions>): LongArray

    @Query("SELECT * FROM competitions")
    suspend fun queryCompetitions(): List<DomainEntities.DomainCompetitions>
}
