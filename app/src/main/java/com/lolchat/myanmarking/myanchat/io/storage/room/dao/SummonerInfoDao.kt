package com.lolchat.myanmarking.myanchat.io.storage.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic
import com.lolchat.myanmarking.myanchat.io.storage.room.model.SummonerInfo

@Dao
abstract class SummonerInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addAll(champList: List<SummonerInfo>): List<Long>

    @Query("SELECT * FROM summoner_info")
    abstract fun getAllSummonerInfo(): List<SummonerInfo>

    @Query("SELECT * FROM summoner_info where summonerId = :summonerId")
    abstract fun getSummonerInfoById(summonerId: Int): SummonerInfo?
}