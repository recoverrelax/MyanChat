package com.lolchat.myanmarking.myanchat.io.other

import com.lolchat.myanmarking.myanchat.io.model.json.JChampionList
import com.lolchat.myanmarking.myanchat.io.model.json.RealmDto
import com.lolchat.myanmarking.myanchat.io.network.ApiService
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsRiotApi
import io.reactivex.Observable

class DynamicUrlBuilder(
        val apiService: ApiService,
        val prefsRiot: PrefsRiotApi
) {

    val API_KEY: String = prefsRiot.apiKey
    val REGION: String = prefsRiot.region
    val STATIC_DATA_V: String = prefsRiot.staticDataVersion
    val DD_VERSION: String = prefsRiot.realmDto.dd

    val DDRAGON_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/"

    private val STATIC_DATA_BASE: String = "https://$REGION.api.riotgames.com/lol/static-data/$STATIC_DATA_V/"
    private val DDRAGON_CHAMP_BASE: String = "$DDRAGON_BASE_URL/$DD_VERSION/img/champion/"


    fun getVersions(): Observable<List<String>>
            = apiService.getVersions(STATIC_DATA_BASE.plus("versions?api_key=$API_KEY"))

    fun getRealmVersions(): Observable<RealmDto>
            = apiService.getRealmVersion(STATIC_DATA_BASE.plus("realms?api_key=$API_KEY"))

    fun getChampions(): Observable<JChampionList>
            = apiService.getChampions(STATIC_DATA_BASE.plus("champions?dataById=true&api_key=$API_KEY"))

    fun getChampIconUrl(champName: String): String {
        return "$DDRAGON_CHAMP_BASE$champName.png"
    }
}