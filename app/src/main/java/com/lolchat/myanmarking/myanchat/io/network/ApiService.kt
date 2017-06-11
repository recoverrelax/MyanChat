package com.lolchat.myanmarking.myanchat.io.network

import com.lolchat.myanmarking.myanchat.io.model.json.JChampionList
import com.lolchat.myanmarking.myanchat.io.model.json.RealmDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService{
    @GET fun getVersions(
            @Url url: String
    ): Observable<List<String>>

    @GET fun getChampions(
            @Url url: String
    ): Observable<JChampionList>

    @GET fun getRealmVersion(
            @Url url: String
    ): Observable<RealmDto>
}