package com.lolchat.myanmarking.myanchat.ui.activity

import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.io.model.json.JChampionList
import com.lolchat.myanmarking.myanchat.io.model.json.RealmDto
import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic
import com.lolchat.myanmarking.myanchat.io.network.ApiServiceImpl
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsRiotApi
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class EntryActivity : AppCompatActivity() {

    @Inject lateinit var apiServiceImpl: ApiServiceImpl
    @Inject lateinit var prefsRiot: PrefsRiotApi
    @Inject lateinit var roomInteractor: RoomInteractor
    @Inject lateinit var xmppManager: XmppManager
    @Inject lateinit var app: Application

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_entry)

        MyApp.INSTANCE.appComponent.inject(this)

        val versions: Observable<List<String>>
                = apiServiceImpl.getVersions()
                .doOnNext { saveVersions(it) }

        val realmVersions: Observable<RealmDto>
                = apiServiceImpl.getRealmVersions()
                .doOnNext { saveRealmVersions(it) }

        val getChampionList: Observable<List<Long>>?
                = apiServiceImpl.getChampions()
                .flatMap { Observable.just(mapToChampionBasic(it)) }
                .flatMap { roomInteractor.replaceAllChampionBasic(it) }

        Observable.merge(versions, realmVersions, getChampionList)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {},
                        { Timber.e(it) },
                        {
                            xmppManager.startAndBind(app)
                            MainActivity.startActivity(this@EntryActivity)
                            this@EntryActivity.finish()
                        }
                )
    }

    private fun saveRealmVersions(realmVersions: RealmDto) {
        this.prefsRiot.realmDto = realmVersions
    }

    private fun mapToChampionBasic(list: JChampionList): List<ChampionBasic> {
        val v = list.version
        return list.data.map { it.value }.map { ChampionBasic(it.id, it.name, it.key, it.title, v) }
    }

    private fun saveVersions(list: List<String>): String {
        if (list[0] != EMPTY_STRING) {
            prefsRiot.latestRealmVersion = list[0]
            return list[0]
        }
        return EMPTY_STRING
    }

    override fun onBackPressed() {
        // disable
    }
}