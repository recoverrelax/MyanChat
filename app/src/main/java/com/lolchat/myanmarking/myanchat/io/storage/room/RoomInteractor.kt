package com.lolchat.myanmarking.myanchat.io.storage.room

import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic
import io.reactivex.Flowable
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
}