package com.lolchat.myanmarking.myanchat.io.enums

import com.lolchat.myanmarking.myanchat.R

enum class GameStatus(
        val description: Int,
        val color: Int
) {
    IN_QUEUE(R.string.game_status_in_queue, R.color.game_status_inqueu),
    SPECTATING(R.string.game_status_spectating, R.color.game_status_spectating),
    CHAMPION_SELECT(R.string.game_status_champion_select, R.color.game_status_champ_select),
    INGAME(R.string.game_status_champion_ingame, R.color.game_status_ingame),
    AWAY(R.string.away, R.color.game_status_away),
    NONE(R.string.empty_string, R.color.game_status_none);

    companion object {
        fun getByXmppStanza(stanza: String): GameStatus {
            return when (stanza) {
                "inQueue" -> IN_QUEUE
                "spectating" -> SPECTATING
                "championSelect" -> CHAMPION_SELECT
                "inGame" -> INGAME
                else -> NONE
            }
        }
    }
}