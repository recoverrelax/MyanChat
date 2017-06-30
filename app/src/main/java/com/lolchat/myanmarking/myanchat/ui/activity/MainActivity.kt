package com.lolchat.myanmarking.myanchat.ui.activity

import android.animation.ValueAnimator
import android.app.Application
import android.os.Bundle
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.BaseActivity
import com.lolchat.myanmarking.myanchat.io.model.json.JChampionList
import com.lolchat.myanmarking.myanchat.io.model.json.RealmDto
import com.lolchat.myanmarking.myanchat.io.network.ApiServiceImpl
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsRiotApi
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
import com.lolchat.myanmarking.myanchat.other.Navigator
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragBsOptionsCb
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import android.animation.ArgbEvaluator
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.other.util.colorFromRes


class MainActivity : BaseActivity(), FragBsOptionsCb {

    override val layoutRes: Int = R.layout.act_main

    @Inject lateinit var apiServiceImpl: ApiServiceImpl
    @Inject lateinit var prefsRiot: PrefsRiotApi
    @Inject lateinit var roomInteractor: RoomInteractor
    @Inject lateinit var xmppManager: XmppManager
    @Inject lateinit var app: Application
    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigator.startWithFragFriendList(this, R.id.container)

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
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {},
                            { Timber.e(it) },
                            {
                                xmppManager.startAndBind(app)
                                window.setBackgroundDrawable(null)
                            }
                    )
        }
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

    fun changeStatusBarColor(color: Int, animate: Boolean){
        val newColor = colorFromRes(color)

        if(!animate){
            window.statusBarColor = newColor
        }else{
            //we need old color
            val oldColor = window.statusBarColor

            val anim = ValueAnimator()
            anim.setIntValues(oldColor, newColor)
            anim.setEvaluator(ArgbEvaluator())
            anim.addUpdateListener { valueAnimator -> window.statusBarColor = valueAnimator.animatedValue as Int }
            anim.duration = 300

            anim.start()
        }
    }

    override fun onFriendMatchListClicked(friendName: String) {
        navigator.goToFriendMatchList(this, R.id.container, friendName)
    }
}