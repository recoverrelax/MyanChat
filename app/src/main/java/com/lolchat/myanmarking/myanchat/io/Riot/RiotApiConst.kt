package com.lolchat.myanmarking.myanchat.io.Riot

import com.lolchat.myanmarking.myanchat.R

object RiotApiConst{
    private val DDRAGON_ICON_URL = "http://ddragon.leagueoflegends.com/cdn/"
    private val DDRAGON_VERSION = "7.9.1/"
    private val DDRAGON_ICON_EXT = ".png"
    private val DDRAGON_ICON_POSTFIZ = "img/profileicon/"

    val DDRAGON_DEFAULT_PROFILE_IC = R.drawable.default_sum_icon

    fun getProfileIconUrl(icon: String): String{
        return "$DDRAGON_ICON_URL$DDRAGON_VERSION$DDRAGON_ICON_POSTFIZ$icon$DDRAGON_ICON_EXT"
    }
}