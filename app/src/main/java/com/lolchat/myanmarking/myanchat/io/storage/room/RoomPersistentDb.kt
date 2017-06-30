package com.lolchat.myanmarking.myanchat.io.storage.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.lolchat.myanmarking.myanchat.io.storage.room.dao.ChampionBasicDao
import com.lolchat.myanmarking.myanchat.io.storage.room.dao.SummonerInfoDao
import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic
import com.lolchat.myanmarking.myanchat.io.storage.room.model.SummonerInfo


@Database(
        entities = arrayOf(
                ChampionBasic::class,
                SummonerInfo::class
        ),
        version = 1
)
abstract class RoomPersistentDb: RoomDatabase() {
    abstract fun championBasicDao(): ChampionBasicDao
    abstract fun summonerInfoDao(): SummonerInfoDao

    companion object{
        private const val DB_NAME = "maynchat_roomdb"

        fun createPersistentDatabase(context: Context): RoomPersistentDb
                = Room.databaseBuilder(context.applicationContext, RoomPersistentDb::class.java, DB_NAME).build()
    }
}
