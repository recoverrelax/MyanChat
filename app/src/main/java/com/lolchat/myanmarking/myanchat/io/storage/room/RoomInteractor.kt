package com.lolchat.myanmarking.myanchat.io.storage.room

import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic
import com.lolchat.myanmarking.myanchat.io.storage.room.model.SummonerInfo
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

class RoomInteractor(
        val db: RoomPersistentDb
) {

    fun replaceAllChampionBasic(list: List<ChampionBasic>): Observable<List<Long>> {
        return Observable.fromCallable { db.championBasicDao().addAll(list) }
    }

    fun getAllChamp(): Observable<List<ChampionBasic>>{
        return Observable.fromCallable { db.championBasicDao().getAllChamps() }
    }

    fun getSummonerInfoById(summonerId: Int): Maybe<SummonerInfo>{
        return Maybe.fromCallable { db.summonerInfoDao().getSummonerInfoById(summonerId) }
    }

    fun saveSummonerInfo(summonerInfo: SummonerInfo): Observable<List<Long>> {
        return Observable.fromCallable { db.summonerInfoDao().addAll(listOf(summonerInfo)) }
    }
}