package com.lolchat.myanmarking.myanchat.ui.view_item

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.view.SquareImageView
import com.lolchat.myanmarking.myanchat.io.Riot.RiotApiConst
import com.lolchat.myanmarking.myanchat.io.interfaces.IRecycleableView
import com.lolchat.myanmarking.myanchat.io.model.item_view.FriendViewModel
import com.lolchat.myanmarking.myanchat.io.model.item_view_interfaces.IFriendViewModel
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.other.Util.drawableFromRes
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import timber.log.Timber
import android.graphics.drawable.Drawable
import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.TintTypedArray.obtainStyledAttributes


class FriendView: RelativeLayout, IFriendViewModel, IRecycleableView {

    private val INFO_ENABLED = true
    private val friendSummonerIcon: SquareImageView
    private val friendName: TextView
    private val friendMessage: TextView
    private val friendStatus: TextView

    companion object{
        val VIEW_MODEL: FriendViewModel = FriendViewModel(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
    }

    private var viewModel: FriendViewModel = VIEW_MODEL

    @RequiresApi(Build.VERSION_CODES.M)
    @Suppress("ConvertSecondaryConstructorToPrimary")
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(context, attrs, defStyleAttr, defStyleRes){
        context.layoutInflater.inflate(R.layout.view_friend, this, true)
        layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dip(80))
        val dp5 = dip(10)
        setPadding(dp5, dp5, dp5, dp5)

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

        friendName.text = viewModel.name
        friendStatus.text = viewModel. presenceStatus
        friendMessage.text = viewModel.statusMessage

        val imageUrl = RiotApiConst.getProfileIconUrl(viewModel.sumIconUrl)
        if(INFO_ENABLED) Timber.i("Trying to load image with url: $imageUrl")

        Glide.with(context)
                .load(imageUrl)
                .error(drawableFromRes(RiotApiConst.DDRAGON_DEFAULT_PROFILE_IC))
                .into(friendSummonerIcon)
    }

    override fun onRecycle() {
        Glide.clear(friendSummonerIcon)
    }
}