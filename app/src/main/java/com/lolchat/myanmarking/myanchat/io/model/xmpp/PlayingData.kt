package com.lolchat.myanmarking.myanchat.io.model.xmpp

import com.lolchat.myanmarking.myanchat.io.enums.GameStatus

data class PlayingData(
        val gameStatus: GameStatus,
        val championName: ChampionId,
        val time: Long
)