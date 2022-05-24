package com.oze.footballfixtures.presentation.di.local.datasource

import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.di.local.room.CompetitionsDao

class LocalDataSourceImpl(private val competitionsDao: CompetitionsDao) : LocalDataSource {

    override suspend fun saveCompetitions(competitions: List<DomainEntities.DomainCompetitions>) {
        competitionsDao.insertCompetitions(competitions)
    }

    override suspend fun getAllCompetitionsFromDb(): List<DomainEntities.DomainCompetitions> {
        return competitionsDao.queryCompetitions()
    }
}