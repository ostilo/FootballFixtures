package com.oze.footballfixtures.presentation.di.datasource

import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus

interface RemoteDataSource {

    suspend fun getAllMatches(date: String): NetworkStatus<DomainEntities.DomainMatchResponse>

    suspend fun getAllCompetitions(): NetworkStatus<DomainEntities.DomainCompetitionResponse>

    suspend fun getStandings(id: Long): NetworkStatus<DomainEntities.DomainStandingResponse>

    suspend fun getSingleMatch(id: Long, date: String): NetworkStatus<DomainEntities.DomainMatchResponse>

    suspend fun getTeam(id: Long): NetworkStatus<DomainEntities.DomainTeamResponse>

    suspend fun getPlayers(id: Long): NetworkStatus<DomainEntities.DomainPlayerResponse>

}