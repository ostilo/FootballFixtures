package com.oze.footballfixtures.presentation.di.local.datasource

import com.oze.footballfixtures.presentation.DomainEntities

interface LocalDataSource {

    suspend fun saveCompetitions(competitions: List<DomainEntities.DomainCompetitions>)

    suspend fun getAllCompetitionsFromDb(): List<DomainEntities.DomainCompetitions>

}