package com.lolchat.myanmarking.myanchat.ui.view_item

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.view.SquareImageView
import com.lolchat.myanmarking.myanchat.io.model.item_view.FriendViewModel
import com.lolchat.myanmarking.myanchat.io.model.item_view_interfaces.IFriendViewModel
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.other.Util.drawableFromRes
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater

class FriendView: RelativeLayout, IFriendViewModel {

    private val friendSummonerIcon: SquareImageView
    private val friendName: TextView
    private val friendMessage: TextView
    private val friendStatus: TextView

    companion object{
        val VIEW_MODEL: FriendViewModel = FriendViewModel(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
    }

    private var viewModel: FriendViewModel = VIEW_MODEL

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(context, attrs, defStyleAttr, defStyleRes){
        context.layoutInflater.inflate(R.layout.view_friend, this, true)
        val dp20 = dip(20)
        val dp10 = dip(10)
        setPadding(dp10, dp20, dp10, dp20)

        friendSummonerIcon = find(R.id.friendSummonerIcon)
        friendName = find(R.id.friendName)
        friendMessage = find(R.id.friendMessage)
        friendStatus = find(R.id.friendStatus)

        background = drawableFromRes(R.drawable.divider_itemview)
    }

    override fun setItem(viewModel: FriendViewModel) {
        this.viewModel = viewModel
        bindModelToView(viewModel)
    }

    private fun bindModelToView(viewModel: FriendViewModel){
        viewModel.run {
            friendName.text = name
            friendStatus.text = presenceStatus
            friendMessage.text = statusMessage
        }
    }
}