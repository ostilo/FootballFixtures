package com.oze.footballfixtures.presentation.di.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.oze.footballfixtures.presentation.DomainEntities

class Converters {

    @TypeConverter
    fun fromSeason(season: DomainEntities.DomainSeason): String {
        return  Gson().toJson(season)
       // return Moshi.Builder().build().adapter(DomainEntities.DomainSeason::class.java).toJson(season)
    }

    @TypeConverter
    fun toSeason(season: String): DomainEntities.DomainSeason {
        return Gson().fromJson(season,DomainEntities.DomainSeason::class.java)

       // return Moshi.Builder().build().adapter(DomainEntities.DomainSeason::class.java).fromJson(season)!!
    }
}