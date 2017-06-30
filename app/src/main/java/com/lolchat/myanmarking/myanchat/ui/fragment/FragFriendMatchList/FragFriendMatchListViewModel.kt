package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList

import com.lolchat.myanmarking.myanchat.base.mvp.BaseViewModel
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.io.storage.room.model.SummonerInfo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FragFriendMatchListViewModel(
        val xmppManager: IXmppManager,
        val roomInteractor: RoomInteractor
): BaseViewModel<FragFriendMatchListViewStates>() {

    override fun onCleared() {
        super.onCleared()
    }

    fun onResume() {
    }

    fun saveSummonerInfoToRoom(friendEntity: FriendEntity) {
        val summId = friendEntity.getSummonerId()
        val skinInfo = friendEntity.skinInfo

        Timber.i("SkinInfo: $skinInfo, summId: $summId")

        if(summId == -1 || skinInfo == null){
            return
        }

        val si = SummonerInfo(
                friendEntity.getSummonerId(),
                friendEntity.sumIconUrl,
                friendEntity.skinInfo.skinName,
                friendEntity.skinInfo.skinVariant
        )

        Timber.i("si:: $si")

        roomInteractor.saveSummonerInfo(si)
                .subscribeOn(schedulersProvider.io)
                .subscribe(
                        { Timber.i("Successfully saved SummonerInfo: $summId") },
                        { Timber.e(it) }
                )
    }
}