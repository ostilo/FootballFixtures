package com.oze.footballfixtures.presentation.di

import android.content.Context
import androidx.room.Room
import com.oze.footballfixtures.presentation.di.local.room.AppDatabase
import com.oze.footballfixtures.presentation.di.local.room.AppDatabase.Companion.DATABASE_NAME
import com.oze.footballfixtures.presentation.di.local.room.CompetitionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    /*
     * The method returns the Database object
     **/
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration() // allows database to be cleared after upgrading version
        .build()

    /*
    * We need the CompetitionsDao module.
    * For this, We need the FootballDatabase object
    * So we will define the providers for this here in this module.
    * */
    @Singleton
    @Provides
    fun provideCompetitionDao(footballDatabase: AppDatabase):
            CompetitionsDao = footballDatabase.competitionsDao()
}