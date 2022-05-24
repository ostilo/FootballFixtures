package com.oze.footballfixtures.ui.competition

import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface CompetitionsRepository {

    suspend fun getTodayMatches(date: String): NetworkStatus<DomainEntities.DomainMatchResponse>

    suspend fun getAllCompetitions(): Flow<NetworkStatus<List<DomainEntities.DomainCompetitions>>>

    suspend fun getStandings(id: Long): NetworkStatus<DomainEntities.DomainStandingResponse>

    suspend fun getSingleMatch(id: Long, date: String): NetworkStatus<DomainEntities.DomainMatchResponse>

    suspend fun getTeam(id: Long): NetworkStatus<DomainEntities.DomainTeamResponse>

    suspend fun getPlayers(id: Long): NetworkStatus<DomainEntities.DomainPlayerResponse>

}