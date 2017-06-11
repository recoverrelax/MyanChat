package com.lolchat.myanmarking.myanchat.io.storage.prefs

import android.content.SharedPreferences
import com.lolchat.myanmarking.myanchat.base.prefs.BasePref
import com.lolchat.myanmarking.myanchat.io.model.json.RealmDto
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.squareup.moshi.Moshi

class PrefsRiotApi(
        pref: SharedPreferences,
        moshi: Moshi
) : BasePref(pref, moshi) {

    private val LATEST_REALM_VERSION = "LATEST_REALM_VERSION"
    private val API_KEY = "API_KEY"
    private val REGION = "REGION"
    private val STATIC_DATA_VERSION = "STATIC_DATA_VERSION"
    private val REALM_DTO = "REALM_DTO"

    var latestRealmVersion: String
        get() = get(LATEST_REALM_VERSION, EMPTY_STRING)
        set(value) = put(LATEST_REALM_VERSION, value)

    var apiKey: String
        get() = get(API_KEY, "a4426067-5e18-4802-8386-8276c93757d6")
        set(value) = put(API_KEY, value)

    var region: String
        get() = get(REGION, "euw1")
        set(value) = put(REGION, value)

    var realmDto: RealmDto
        get() = getPojo(REALM_DTO, RealmDto.DEFAULT)
        set(value) = putPojo(REALM_DTO, value)

    var staticDataVersion: String
        get() = get(STATIC_DATA_VERSION, "v3")
        set(value) = put(STATIC_DATA_VERSION, value)
}