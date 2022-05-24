package com.oze.footballfixtures.presentation.di

import com.oze.footballfixtures.core.DefaultDispatcherProvider
import com.oze.footballfixtures.core.DispatcherProvider
import com.oze.footballfixtures.presentation.di.datasource.RemoteDataSource
import com.oze.footballfixtures.presentation.di.datasource.RemoteDataSourceImpl
import com.oze.footballfixtures.presentation.di.local.datasource.LocalDataSource
import com.oze.footballfixtures.presentation.di.local.datasource.LocalDataSourceImpl
import com.oze.footballfixtures.presentation.di.local.room.AppDatabase
import com.oze.footballfixtures.presentation.di.local.room.CompetitionsDao
import com.oze.footballfixtures.presentation.di.remote.ApiService
import com.oze.footballfixtures.ui.competition.CompetitionsRepository
import com.oze.footballfixtures.ui.competition.CompetitionsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module(includes = [NetworkModule::class])
@Module
@InstallIn(SingletonComponent::class)
object DataModule {


   // @Singleton
   @[Provides Singleton]
    fun provideCompetitionsRepository(
        dispatcherProvider: DispatcherProvider,
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): CompetitionsRepository {
        return CompetitionsRepositoryImpl(dispatcherProvider, remoteDataSource, localDataSource)
    }

    @[Provides Singleton]
    internal fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()




    @[Provides Singleton]
    fun provideLocalDataSource(competitionsDao: CompetitionsDao): LocalDataSource {
        return LocalDataSourceImpl(competitionsDao)
    }

//    @Provides
//    fun provideCompetitionDAO(appDatabase: AppDatabase): CompetitionsDao {
//        return appDatabase.competitionsDao()
//    }


    @[Provides Singleton]
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

}