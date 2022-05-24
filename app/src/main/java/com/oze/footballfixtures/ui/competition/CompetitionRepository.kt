package com.oze.footballfixtures.ui.competition

import com.oze.footballfixtures.core.DispatcherProvider
import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.presentation.di.datasource.RemoteDataSource
import com.oze.footballfixtures.presentation.di.local.datasource.LocalDataSource
import com.oze.footballfixtures.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CompetitionsRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CompetitionsRepository {

    override suspend fun getTodayMatches(date: String): NetworkStatus<DomainEntities.DomainMatchResponse> {
        return withContext(dispatcherProvider.io()) { remoteDataSource.getAllMatches(date) }
    }

    //Annotation
    override suspend fun getAllCompetitions(): Flow<NetworkStatus<List<DomainEntities.DomainCompetitions>>> {
        return networkBoundResource(
            query = { fetchLocalCompetitions() },
            fetch = { remoteDataSource.getAllCompetitions() },
            saveFetchResult = { response ->
                localDataSource.saveCompetitions(response.data?.competitions!!)
            },
            clearData = {}
        )
    }

    private fun fetchLocalCompetitions(): Flow<List<DomainEntities.DomainCompetitions>> = flow {
        emit(localDataSource.getAllCompetitionsFromDb())
    }

    override suspend fun getStandings(id: Long): NetworkStatus<DomainEntities.DomainStandingResponse> {
        return withContext(dispatcherProvider.io()) { remoteDataSource.getStandings(id) }
    }

    override suspend fun getSingleMatch(
        id: Long,
        date: String
    ): NetworkStatus<DomainEntities.DomainMatchResponse> {
        return withContext(dispatcherProvider.io()) { remoteDataSource.getSingleMatch(id, date) }
    }

    override suspend fun getTeam(id: Long): NetworkStatus<DomainEntities.DomainTeamResponse> {
        return withContext(dispatcherProvider.io()) {
            remoteDataSource.getTeam(id)
        }
    }

    override suspend fun getPlayers(id: Long): NetworkStatus<DomainEntities.DomainPlayerResponse> {
        return withContext(dispatcherProvider.io()) {
            remoteDataSource.getPlayers(id)
        }
    }

}