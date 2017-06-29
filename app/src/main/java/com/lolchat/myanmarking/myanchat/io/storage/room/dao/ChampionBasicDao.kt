package com.lolchat.myanmarking.myanchat.io.storage.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic

@Dao
abstract class ChampionBasicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addAll(champList: List<ChampionBasic>): List<Long>

    @Query("SELECT * FROM champion_basic")
    abstract fun getAllChamps(): List<ChampionBasic>
}