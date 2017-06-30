package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList

import com.lolchat.myanmarking.myanchat.base.mvp.BaseViewModel
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.io.model.xmpp.SkinInfo
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.io.storage.room.model.SummonerInfo
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

    fun loadBasicInfo(friendEntity: FriendEntity?){
        if (friendEntity != null) {

            if (friendEntity.skinInfo != null) {

                saveSummonerInfoToRoom(friendEntity, {
                    postEvent(FragFriendMatchListViewStates.LoadBasicInfo(
                            friendEntity.isFriendOnline, friendEntity.sumIconId, friendEntity.skinInfo
                    ))
                })
            } else {
                // if null, try to get from the database
                val xmppId = friendEntity.getSummonerId()
                val hasIconId = friendEntity.sumIconId != EMPTY_STRING

                if (xmppId != -1 || !hasIconId) {
                    getSummonerInfoFromRoom(friendEntity.getSummonerId(), {
                        postEvent(FragFriendMatchListViewStates.LoadBasicInfo(
                                friendEntity.isFriendOnline,
                                if(hasIconId) friendEntity.sumIconId else it.summIconId,
                                SkinInfo(it.lastSkinName, it.lastSkinVariant)
                        ))
                    })
                }else{
                    postEvent(FragFriendMatchListViewStates.LoadBasicInfo(
                            friendEntity.isFriendOnline, friendEntity.sumIconId, null
                    ))
                }
            }
        }else{
            postEvent(FragFriendMatchListViewStates.LoadBasicInfo(
                    true, EMPTY_STRING, null
            ))
        }
    }

    private fun saveSummonerInfoToRoom(friendEntity: FriendEntity, doOnSuccess: () -> Unit) {
        val summId = friendEntity.getSummonerId()
        val skinInfo = friendEntity.skinInfo

        Timber.i("SkinInfo: $skinInfo, summId: $summId")

        if(summId == -1 || skinInfo == null){
            return
        }

        val si = SummonerInfo(
                friendEntity.getSummonerId(),
                friendEntity.sumIconId,
                friendEntity.skinInfo.skinName,
                friendEntity.skinInfo.skinVariant
        )

        roomInteractor.saveSummonerInfo(si)
                .subscribeOn(schedulersProvider.io)
                .subscribe(
                        {
                            doOnSuccess.invoke()
                            Timber.i("Successfully saved SummonerInfo: $summId")
                        },
                        { Timber.e(it) }
                )
    }

    private fun getSummonerInfoFromRoom(summonerId: Int, doOnSuccess: (SummonerInfo) -> Unit) {
        roomInteractor.getSummonerInfoById(summonerId)
                .subscribeOn(schedulersProvider.io)
                .subscribe(
                        {
                            doOnSuccess.invoke(it)
                        },
                        { Timber.e(it) }
                )
    }
}