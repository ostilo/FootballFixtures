package com.oze.footballfixtures.ui.compete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.presentation.map
import com.oze.footballfixtures.ui.competition.usecase.competedetails.GetFixtureUseCase
import com.oze.footballfixtures.ui.competition.usecase.competedetails.GetFixtureUseCase.Companion.make
import com.oze.footballfixtures.ui.competition.usecase.competedetails.GetPlayersUseCase
import com.oze.footballfixtures.ui.competition.usecase.competedetails.GetTableUseCase
import com.oze.footballfixtures.ui.competition.usecase.competedetails.GetTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompetitionDetailsViewModel @Inject constructor(
    private val getTableUseCase: GetTableUseCase,
    private val getFixtureUseCase: GetFixtureUseCase,
    private val getTeamUseCase: GetTeamUseCase,
    private val getPlayersUseCase: GetPlayersUseCase
) : ViewModel() {

    fun getStandings(id: Long) = liveData {
        emit(NetworkStatus.Loading())
        when (val result = getTableUseCase.invoke(id)) {
            is NetworkStatus.Success -> emit(NetworkStatus.Success(result.data?.map()))
            is NetworkStatus.Error -> emit(NetworkStatus.Error(result.errorMessage, null))

        }
    }

    fun getSingleMatch(id: Long, date: String) = liveData {
        emit(NetworkStatus.Loading())
        when (val result = getFixtureUseCase.invoke(make(id, date))) {
            is NetworkStatus.Success -> emit(NetworkStatus.Success(result.data?.map()))
            is NetworkStatus.Error -> emit(NetworkStatus.Error(result.errorMessage, null))
        }
    }

    fun getTeams(id: Long) = liveData {
        emit(NetworkStatus.Loading())
        when (val result = getTeamUseCase.invoke(id)) {
            is NetworkStatus.Success -> emit(NetworkStatus.Success(result.data?.map()))
            is NetworkStatus.Error -> emit(NetworkStatus.Error(result.errorMessage, null))
        }
    }

    fun getPlayers(id: Long) = liveData {
        emit(NetworkStatus.Loading())
        when (val result = getPlayersUseCase.invoke(id)) {
            is NetworkStatus.Success -> emit(NetworkStatus.Success(result.data?.map()))
            is NetworkStatus.Error -> emit(NetworkStatus.Error(result.errorMessage, null))
        }
    }

}
