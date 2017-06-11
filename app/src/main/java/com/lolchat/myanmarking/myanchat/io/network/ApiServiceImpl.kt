package com.lolchat.myanmarking.myanchat.io.network

import com.lolchat.myanmarking.myanchat.io.model.json.JChampionList
import com.lolchat.myanmarking.myanchat.io.model.json.RealmDto
import com.lolchat.myanmarking.myanchat.io.other.DynamicUrlBuilder
import io.reactivex.Observable

class ApiServiceImpl(
        val urlBuilder: DynamicUrlBuilder
) {

    fun getVersions(): Observable<List<String>> = urlBuilder.getVersions()
    fun getRealmVersions(): Observable<RealmDto> = urlBuilder.getRealmVersions()
    fun getChampions(): Observable<JChampionList> = urlBuilder.getChampions()
}