package com.lolchat.myanmarking.myanchat.io.model.xmpp

import com.lolchat.myanmarking.myanchat.io.enums.GameStatus
import com.lolchat.myanmarking.myanchat.io.enums.PresenceMode
import com.lolchat.myanmarking.myanchat.io.enums.RankedLeagueTierDivision

data class FriendEntity(
        var name: String,
        var sumIconUrl: String,
        var statusMessage: String,
        var isFriendOnline: Boolean,
        var gameStatus: GameStatus,
        var presenceMode: PresenceMode,
        var tierAndDivision: RankedLeagueTierDivision,
        val playingData: PlayingData?
) {

    companion object{
        val EMPTY = FriendEntity("", "", "", false, GameStatus.NONE, PresenceMode.OFFLINE, RankedLeagueTierDivision.NONE, null)
    }

    fun sameAs(friendEntity: FriendEntity): Boolean {
        return this.name == friendEntity.name &&
                this.sumIconUrl == friendEntity.sumIconUrl &&
                this.statusMessage == friendEntity.statusMessage &&
                this.isFriendOnline == friendEntity.isFriendOnline &&
                this.gameStatus == friendEntity.gameStatus &&
                this.presenceMode == friendEntity.presenceMode &&
                this.tierAndDivision == friendEntity.tierAndDivision &&
                this.playingData == friendEntity.playingData
    }

    fun isOnlineInChampSelectOrPlaying(): Boolean {
        val pd = playingData ?: return false
        return pd.gameStatus == GameStatus.CHAMPION_SELECT || pd.gameStatus == GameStatus.INGAME
    }

}