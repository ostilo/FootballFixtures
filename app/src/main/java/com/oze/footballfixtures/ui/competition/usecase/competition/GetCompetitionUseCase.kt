package com.oze.footballfixtures.ui.competition.usecase.competition

import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.ui.competition.CompetitionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompetitionUseCase @Inject constructor(
    private val competitionsRepository: CompetitionsRepository
) {

    suspend operator fun invoke(): Flow<NetworkStatus<List<DomainEntities.DomainCompetitions>>> {
        return competitionsRepository.getAllCompetitions()
    }

}