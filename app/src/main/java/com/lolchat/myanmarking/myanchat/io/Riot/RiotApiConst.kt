package com.lolchat.myanmarking.myanchat.io.Riot

import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsRiotApi

class RiotApiConst(
        riotApiPrefs: PrefsRiotApi
) {
    private val DDRAGON_ICON_URL = riotApiPrefs.realmDto.cdn
    private val DDRAGON_VERSION: String = "/${riotApiPrefs.realmDto.dd}/"

    private val DDRAGON_PROFILE_ICON_EXT = ".png"
    private val DDRAGON_PROFILE_ICON_POSTFIX = "img/profileicon/"

    private val DDRAGON_SKIN_VARIANT_EXT = ".jpg"
    private val DDRAGON_SKIN_VARIANT_POSTFIX = "/img/champion/splash/"

    val DDRAGON_DEFAULT_PROFILE_IC = R.drawable.default_sum_icon
    val DDRAGON_DEFAULT_NO_CHAMP = R.drawable.ic_cancel_black_24dp

    fun getProfileIconUrl(icon: String): String {
        return "$DDRAGON_ICON_URL$DDRAGON_VERSION$DDRAGON_PROFILE_ICON_POSTFIX$icon$DDRAGON_PROFILE_ICON_EXT"
    }

    fun getSkinVariantUrl(skinName: String): String{
        return "$DDRAGON_ICON_URL$DDRAGON_SKIN_VARIANT_POSTFIX$skinName$DDRAGON_SKIN_VARIANT_EXT"
    }
}