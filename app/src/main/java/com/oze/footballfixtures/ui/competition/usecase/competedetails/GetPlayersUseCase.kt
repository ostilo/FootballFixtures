package com.oze.footballfixtures.ui.competition.usecase.competedetails

import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.ui.competition.CompetitionsRepository
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor (
    private val competitionsRepository: CompetitionsRepository
) {

    suspend operator fun invoke(input: Long?): NetworkStatus<DomainEntities.DomainPlayerResponse> {
        return competitionsRepository.getPlayers(input!!)
    }

}