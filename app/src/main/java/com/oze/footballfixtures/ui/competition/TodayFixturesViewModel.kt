package com.oze.footballfixtures.ui.competition

import androidx.lifecycle.*
import com.oze.footballfixtures.presentation.Competitions
import com.oze.footballfixtures.presentation.DomainEntities
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.presentation.map
import com.oze.footballfixtures.ui.competition.usecase.competition.GetCompetitionUseCase
import com.oze.footballfixtures.ui.competition.usecase.GetTodayFixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.launch
import  kotlinx.coroutines.flow.collect

@HiltViewModel
class TodayFixturesViewModel @Inject constructor(
    private val getCompetitionUseCase: GetCompetitionUseCase,
    private val getTodayFixUseCase: GetTodayFixUseCase
) : ViewModel() {

    private val _competitionsLiveData = MutableLiveData<NetworkStatus<List<Competitions>>>()
    val competitionsLiveData = _competitionsLiveData

    fun getAllCompetitions() {
        viewModelScope.launch(Dispatchers.IO) {
            getCompetitionUseCase.invoke().collect {
                updateData(it)
            }

        }
    }


    private fun updateData(response: NetworkStatus<List<DomainEntities.DomainCompetitions>>) {
        when (response) {
            is NetworkStatus.Error -> {
                if (response.data.isNullOrEmpty().not()) {
                    _competitionsLiveData.postValue(
                        NetworkStatus.Error(
                            null,
                            response.data?.map { it.map() })
                    )
                } else {
                    _competitionsLiveData.postValue(
                        NetworkStatus.Error(
                            response.errorMessage,
                            null
                        )
                    )
                }
            }
            is NetworkStatus.Loading -> {
                if (response.data.isNullOrEmpty().not()) {
                    _competitionsLiveData.postValue(NetworkStatus.Loading(response.data?.map { it.map() }))
                } else {
                    _competitionsLiveData.postValue(NetworkStatus.Loading())
                }
            }
            is NetworkStatus.Success -> {
                if (response.data.isNullOrEmpty().not()) {
                    _competitionsLiveData.postValue(NetworkStatus.Success(response.data?.map { it.map() }))
                }
            }
        }
    }

    fun getAllMatches(date: String) = liveData{
        emit(NetworkStatus.Loading())
        when (val result = getTodayFixUseCase.invoke(date)) {
            is NetworkStatus.Success -> emit(NetworkStatus.Success(result.data?.map()))
            is NetworkStatus.Error -> emit(NetworkStatus.Error(result.errorMessage!!, null))
        }
    }


}

