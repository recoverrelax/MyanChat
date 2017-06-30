package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.TextView
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.BaseFragment
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.io.storage.room.model.SummonerInfo
import com.lolchat.myanmarking.myanchat.other.XmppImageHelperImpl
import com.lolchat.myanmarking.myanchat.ui.activity.MainActivity
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.IFragFriendListView
import kotlinx.android.synthetic.main.frag_friend_match_list.*
import timber.log.Timber
import javax.inject.Inject

class FragFriendMatchList : BaseFragment<FragFriendMatchListViewModel, FragFriendMatchListViewStates>(), IFragFriendListView {

    @Inject lateinit var mainActivity: MainActivity
    @Inject lateinit var xmppImageHelper: XmppImageHelperImpl

    override val layoutRes: Int = R.layout.frag_friend_match_list

    companion object {
        val FRIEND_NAME = "friendName"
        val TAG = "FragFriendMatchList.TAG"
        fun newInstance(friendName: String): FragFriendMatchList {
            val frag = FragFriendMatchList()
            val bundle = Bundle().apply {
                putString(FRIEND_NAME, friendName)
            }
            frag.arguments = bundle
            return frag
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.changeStatusBarColor(android.R.color.transparent, true)

        val appBarListener = MyAppBarChangeListener()

        appbar.run { addOnOffsetChangedListener(appBarListener) }
        back.setOnClickListener { mainActivity.onBackPressed() }

        val friendName = arguments.getString(FRIEND_NAME, EMPTY_STRING)
        if (friendName == EMPTY_STRING) {
            throw IllegalStateException("Must pass a valid intent with a valid userName")
        }

        val friendEntity: FriendEntity? = viewModel.xmppManager.getUpdatedFriendEntityByName(friendName)
        Timber.i("FriendEntity: $friendEntity")

        if (friendEntity != null) {
            xmppImageHelper.loadProfileIconUrl(
                    context,
                    friendEntity.sumIconUrl,
                    friendSummonerIcon,
                    true
            )

            val skinInfo = friendEntity.skinInfo

            if (skinInfo != null) {
                xmppImageHelper.loadSkin(context, friendEntity.skinInfo, collapsingImage)
                viewModel.saveSummonerInfoToRoom(friendEntity)
            } else {
                // if null, try to get from the database


                collapsingImage.setImageDrawable(null)
            }

        }
    }

    override fun render(viewState: FragFriendMatchListViewStates) {

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    inner class MyAppBarChangeListener : AppBarLayout.OnOffsetChangedListener, AppBarOffSetProvider {

        private var appBarTotalScrollRange: Int = 0
        override var currentOffSet: Int = 0
            private set
        private val textView1: TextView = title1
        private val textView2: TextView = title2

        override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
            this.currentOffSet = Math.abs(verticalOffset)
            if (appBarTotalScrollRange == 0) {
                appBarTotalScrollRange = appBarLayout.totalScrollRange
            }

            val total = currentOffSet.toFloat() / appBarTotalScrollRange.toFloat()
            changeToolbarTextViewAlphas(total)
        }

        fun changeToolbarTextViewAlphas(alphaValue: Float) {
            textView1.alpha = alphaValue
            textView2.alpha = alphaValue
        }
    }

    interface AppBarOffSetProvider {
        val currentOffSet: Int
    }
}