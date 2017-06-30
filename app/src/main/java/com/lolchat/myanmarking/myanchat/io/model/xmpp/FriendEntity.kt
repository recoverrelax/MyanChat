package com.lolchat.myanmarking.myanchat.io.model.xmpp

import com.lolchat.myanmarking.myanchat.io.enums.GameStatus
import com.lolchat.myanmarking.myanchat.io.enums.PresenceMode
import com.lolchat.myanmarking.myanchat.io.enums.RankedLeagueTierDivision

data class FriendEntity(
        private val xmppId: String,
        val name: String,
        val sumIconUrl: String,
        val statusMessage: String,
        val isFriendOnline: Boolean,
        val gameStatus: GameStatus,
        val presenceMode: PresenceMode,
        val tierAndDivision: RankedLeagueTierDivision,
        val playingData: PlayingData?,
        val skinInfo: SkinInfo?
) {
    fun sameAs(friendEntity: FriendEntity): Boolean {
        return this.name == friendEntity.name &&
                this.xmppId == friendEntity.xmppId &&
                this.sumIconUrl == friendEntity.sumIconUrl &&
                this.statusMessage == friendEntity.statusMessage &&
                this.isFriendOnline == friendEntity.isFriendOnline &&
                this.gameStatus == friendEntity.gameStatus &&
                this.presenceMode == friendEntity.presenceMode &&
                this.tierAndDivision == friendEntity.tierAndDivision &&
                this.skinInfo == friendEntity.skinInfo
    }

    fun getSummonerId(): Int{
        return xmppId.replace("sum", "").replace("@pvp.net", "").toIntOrNull() ?: -1
    }

    fun isOnlineInChampSelectOrPlaying(): Boolean {
        val pd = playingData ?: return false
        return pd.gameStatus == GameStatus.CHAMPION_SELECT || pd.gameStatus == GameStatus.INGAME
    }

    companion object {
        val EMPTY = FriendEntity("", "", "", "", false, GameStatus.NONE, PresenceMode.OFFLINE, RankedLeagueTierDivision.NONE, null, null)
    }
}