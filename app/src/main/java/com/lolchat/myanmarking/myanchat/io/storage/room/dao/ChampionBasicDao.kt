package com.lolchat.myanmarking.myanchat.io.storage.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic
import io.reactivex.Flowable

@Dao
abstract class ChampionBasicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addAll(champList: List<ChampionBasic>): List<Long>

    @Query("SELECT name FROM champion_basic where id = :p0 LIMIT 1")
    abstract fun getNameById(champId: Int): String

    @Query("SELECT * FROM champion_basic")
    abstract fun getAllChamps(): Flowable<List<ChampionBasic>>
}