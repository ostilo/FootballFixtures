package com.oze.footballfixtures.ui.competition.usecase.competedetails

import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.ui.competition.CompetitionsRepository
import javax.inject.Inject

class GetFixtureUseCase @Inject constructor(
    private val competitionsRepository: CompetitionsRepository
) {

    suspend operator fun invoke(input: GetParameters?): NetworkStatus<DomainEntities.DomainMatchResponse> {
        return competitionsRepository.getSingleMatch(input?.value1!!, input.value2)
    }

    data class GetParameters(val value1: Long, val value2: String)

    companion object {
        @JvmStatic
        fun make(value1: Long, value2: String): GetParameters =
            GetParameters(
                value1,
                value2
            )

    }
}