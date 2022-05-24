package com.oze.footballfixtures.presentation.di.datasource

import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.presentation.di.remote.ApiService
import com.oze.footballfixtures.utils.safeHttpCall

class RemoteDataSourceImpl (private val apiService: ApiService): RemoteDataSource {

    override suspend fun getAllMatches(date: String): NetworkStatus<DomainEntities.DomainMatchResponse> {
        return safeHttpCall {apiService.getAllMatches(date, date)}
    }

    override suspend fun getAllCompetitions(): NetworkStatus<DomainEntities.DomainCompetitionResponse> {
        return safeHttpCall {apiService.getAllCompetitions("TIER_ONE")}
    }

    override suspend fun getStandings(id: Long): NetworkStatus<DomainEntities.DomainStandingResponse> {
        return safeHttpCall {apiService.getTablesByCompetition(id, "TOTAL")}
    }

    override suspend fun getSingleMatch(id: Long, date: String): NetworkStatus<DomainEntities.DomainMatchResponse> {
        return safeHttpCall {apiService.getMatchesByCompetition(id, date, date)}
    }

    override suspend fun getTeam(id: Long): NetworkStatus<DomainEntities.DomainTeamResponse> {
        return safeHttpCall {apiService.getTeamsByCompetition(id)}
    }

    override suspend fun getPlayers(id: Long): NetworkStatus<DomainEntities.DomainPlayerResponse> {
        return safeHttpCall {apiService.getTeamById(id)}
    }
}