package com.lolchat.myanmarking.myanchat.ui.view_item

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.view.SquareImageView
import com.lolchat.myanmarking.myanchat.io.Riot.RiotApiConst
import com.lolchat.myanmarking.myanchat.io.enums.GameStatus
import com.lolchat.myanmarking.myanchat.io.interfaces.IRecycleableView
import com.lolchat.myanmarking.myanchat.io.model.item_view.FriendViewModel
import com.lolchat.myanmarking.myanchat.io.model.item_view_interfaces.IFriendViewModel
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.other.util.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater


class FriendView : FrameLayout, IFriendViewModel, IRecycleableView {

    private val INFO_ENABLED = true
    private val friendSummonerIcon: SquareImageView
    private val friendName: TextView
    private val friendStatus: TextView

    companion object {
        val VIEW_MODEL: FriendViewModel = FriendViewModel(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, false, GameStatus.NONE)
    }

    private var viewModel: FriendViewModel = VIEW_MODEL

    private val ONLINE_BG_COLOR: Int
    private val OFFLINE_BG_COLOR: Int

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        context.layoutInflater.inflate(R.layout.view_friend, this, true)
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dip(80))
        val dp10 = dip(10)
        setPadding(dp10, dp10, dp10, dp10)

        friendSummonerIcon = find(R.id.friendSummonerIcon)
        friendName = find(R.id.friendName)
        friendStatus = find(R.id.friendStatus)

        ONLINE_BG_COLOR = colorFromRes(R.color.green_700_a50)
        OFFLINE_BG_COLOR = colorFromRes(R.color.red_700_a50)
    }

    override fun setItem(viewModel: FriendViewModel) {
        this.viewModel.rebind(viewModel)
        bindModelToView()
    }

    private fun bindModelToView() {

        friendName.text = viewModel.name
        friendStatus.text = viewModel.presenceStatus

        bindGameStatus()

        val imageUrl = RiotApiConst.getProfileIconUrl(viewModel.sumIconUrl)
        if (viewModel.sumIconUrl != EMPTY_STRING) {
            Glide.with(context)
                    .load(imageUrl)
                    .error(drawableFromRes(RiotApiConst.DDRAGON_DEFAULT_PROFILE_IC))
                    .into(friendSummonerIcon)
        } else {
            friendSummonerIcon.setImageDrawable(drawableFromRes(RiotApiConst.DDRAGON_DEFAULT_PROFILE_IC))
        }

        this.backgroundDrawable = ColorDrawable(
                if (viewModel.isFriendOnline) ONLINE_BG_COLOR else OFFLINE_BG_COLOR
        )
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun bindGameStatus() {
        val gameStatus = viewModel.gameStatus
        val hasGameStatus = gameStatus != GameStatus.NONE

        if (hasGameStatus) {
            friendStatus.text = stringFromRes(gameStatus.description)
            friendStatus.backgroundTintList = ColorStateList.valueOf(colorFromRes(gameStatus.color))
            friendStatus.setVisibleIf()
        } else {
            friendStatus.setGoneIf()
        }
    }

    override fun onRecycle() {
//        Glide.clear(friendSummonerIcon)
    }
}