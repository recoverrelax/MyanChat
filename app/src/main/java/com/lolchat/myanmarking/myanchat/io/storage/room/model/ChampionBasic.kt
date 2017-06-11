package com.lolchat.myanmarking.myanchat.io.storage.room.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "champion_basic")
data class ChampionBasic(
        @PrimaryKey
        val id: Int,
        val name: String,
        val key: String,
        val description: String,
        val version: String
)