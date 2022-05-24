package com.oze.footballfixtures.ui.competition.usecase

import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.ui.competition.CompetitionsRepository
import javax.inject.Inject

class GetTodayFixUseCase @Inject constructor(
    private val competitionsRepository: CompetitionsRepository
) {

    suspend operator fun invoke(input: String?): NetworkStatus<DomainEntities.DomainMatchResponse> {
        return competitionsRepository.getTodayMatches(input.toString())
    }

}