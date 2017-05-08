package com.lolchat.myanmarking.myanchat.io.prefs

import android.content.SharedPreferences
import com.lolchat.myanmarking.myanchat.base.prefs.BasePref
import com.lolchat.myanmarking.myanchat.io.enums.RiotServer
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.io.other.NO_INT
import timber.log.Timber

class PrefsUser(
        pref: SharedPreferences
) : BasePref(pref) {

    private val USERNAME = "PrefsUser.USERNAME"
    private val PASSWORD = "PrefsUser.PASSWORD"
    private val SERVER = "PrefsUser.SERVER"

    var username: String
        get() = get(USERNAME, EMPTY_STRING)
        set(value) = put(USERNAME, value)

    var password: String
        get() = get(PASSWORD, EMPTY_STRING)
        set(value) = put(PASSWORD, value)

    var server: RiotServer
        get(){
            var serverOrdinal = get(SERVER, NO_INT)
            if(serverOrdinal == NO_INT){
                Timber.wtf("The value for the server has not yet been inited")
                serverOrdinal = RiotServer.EUW.ordinal
            }
            return RiotServer.values()[serverOrdinal]
        }
        set(value) = put(SERVER, value.ordinal)

    fun hasUsernamePref() = hasPreference(USERNAME)
}