package com.lolchat.myanmarking.myanchat.io.storage.room.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "summoner_info")
data class SummonerInfo(
        @PrimaryKey val summonerId: Int,
        val lastProfileId: String,
        val lastSkinName: String,
        val lastSkinVariant: String
)