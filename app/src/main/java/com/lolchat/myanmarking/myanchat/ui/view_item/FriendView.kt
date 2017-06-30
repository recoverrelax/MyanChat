package com.lolchat.myanmarking.myanchat.ui.view_item

import android.content.Context
import android.content.res.ColorStateList
import android.support.design.internal.ForegroundLinearLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.io.Riot.RiotApiConst
import com.lolchat.myanmarking.myanchat.io.enums.Collapsable
import com.lolchat.myanmarking.myanchat.io.enums.GameStatus
import com.lolchat.myanmarking.myanchat.io.enums.PresenceMode
import com.lolchat.myanmarking.myanchat.io.enums.RankedLeagueTierDivision
import com.lolchat.myanmarking.myanchat.io.interfaces.IRecycleableView
import com.lolchat.myanmarking.myanchat.io.model.item_view_interfaces.IFriendEntity
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.io.model.xmpp.PlayingData
import com.lolchat.myanmarking.myanchat.io.other.DynamicUrlBuilder
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.other.XmppImageHelperImpl
import com.lolchat.myanmarking.myanchat.other.util.*
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import timber.log.Timber
import java.sql.Timestamp
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class FriendView : ForegroundLinearLayout, IFriendEntity, IRecycleableView {

    private val INFO_ENABLED = true
    private val friendSummonerIcon: CircleImageView
    private val friendName: TextView
    private val friendStatus: TextView
    private val presenceMode: ImageView
    private val playingMessage: TextView
    private val playingChamp: ImageView
    private val leagueTierAndDivisionIcon: ImageView
    private val leagueTierAndDivisionIconText: TextView
    private val container: LinearLayout

    private var collapsableState = Collapsable.COLLAPSED
    private var viewModel: FriendEntity = FriendEntity.EMPTY

    @Inject lateinit var uriBuilder: DynamicUrlBuilder
    @Inject lateinit var riotApiConst: RiotApiConst
    @Inject lateinit var xmppImageHelper: XmppImageHelperImpl

    private var timeStampDisposable: Disposable? = null
        private set

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        context.layoutInflater.inflate(R.layout.view_friend, this, true)
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        orientation = LinearLayout.VERTICAL
        val dp10 = dip(10)
        setPadding(dp10, dp10, dp10, dp10)

        friendSummonerIcon = find(R.id.friendSummonerIcon)
        friendName = find(R.id.friendName)
        friendStatus = find(R.id.friendStatus)
        presenceMode = find(R.id.presenceMode)
        playingMessage = find(R.id.playingMessage)
        playingChamp = find(R.id.playingChamp)
        leagueTierAndDivisionIcon = find(R.id.leagueTierAndDivisionIcon)
        leagueTierAndDivisionIconText = find(R.id.leagueTierAndDivisionIconText)
        container = find(R.id.container)

        foreground = context.getSystemRes(android.R.attr.selectableItemBackgroundBorderless)
        isClickable = true

        MyApp.INSTANCE.appComponent.inject(this)
    }

    override fun setItem(viewModel: FriendEntity) {
        this.viewModel = viewModel
        bindModelToView()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)

        if (visibility == View.GONE) {
            stopTimeStampUpdated()
        } else if (visibility == View.VISIBLE) {
            resumeTimeStampUpdated()
        }
    }

    private fun bindModelToView() {

        friendName.text = viewModel.name

        bindGameStatus()
        bindPresenceMode()
        bindLeagueTierAndDivision()

        val pd = this.viewModel.playingData
        if (pd != null) {
            bindPlayingData(false, pd)
            bindPlayingImage()
        }

        xmppImageHelper.loadProfileIconUrl(
                context,
                viewModel.sumIconUrl,
                friendSummonerIcon,
                viewModel.isFriendOnline
        )
    }

    private fun bindPlayingImage() {
        val pd = this.viewModel.playingData ?: return

        if (pd.championName.name != EMPTY_STRING) {
            val url = uriBuilder.getChampIconUrl(pd.championName.key)
            Timber.i("URL: $url")
            Glide.with(context)
                    .load(uriBuilder.getChampIconUrl(pd.championName.key))
                    .dontAnimate()
                    .error(riotApiConst.DDRAGON_DEFAULT_NO_CHAMP)
                    .into(playingChamp)
        } else {
            playingChamp.setImageDrawable(drawableFromRes(riotApiConst.DDRAGON_DEFAULT_NO_CHAMP))
        }
    }

    private fun getTimeStampInMinutes(pd: PlayingData) = TimeUnit.MILLISECONDS.toMinutes(
            System.currentTimeMillis() - Timestamp(pd.time).time)

    private fun bindPlayingData(isInterval: Boolean, pd: PlayingData) {

        if (!isInterval) {
            timeStampDisposable?.dispose()
        } else {
            if (timeStampDisposable.isNotDisposed()) {
                return
            }
        }

        timeStampDisposable =
                Observable.interval(0, 5, TimeUnit.SECONDS)
                        .map {
                            val timeInMinutes = getTimeStampInMinutes(pd)
                            when {
                                pd.gameStatus.inChampSelect() && pd.time != -1L -> {
                                    context.getString(R.string.friendview_champ_select, timeInMinutes)
                                }
                                pd.gameStatus.inGame() && pd.championName.name != EMPTY_STRING && pd.time != -1L -> {
                                    context.getString(R.string.friendview_playing_as, pd.championName.name, timeInMinutes)
                                }
                                pd.gameStatus.inChampSelect() -> context.getString(R.string.friendview_champ_select_def)
                                pd.gameStatus.inGame() -> context.getString(R.string.friendview_playing_as_def)
                                else -> EMPTY_STRING
                            }
                        }
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnDispose { Timber.i("Disposed") }
                        .doOnSubscribe { Timber.i("Subscribed") }
                        .subscribe(
                                {
                                    if (it != EMPTY_STRING) {
                                        playingMessage.text = it
                                    }
                                    Timber.i("Updating Tv")
                                },
                                { Timber.e(it) }
                        )
    }

    private fun bindGameStatus() {
        val gameStatus = viewModel.gameStatus
        val hasGameStatus = gameStatus != GameStatus.NONE

        if (hasGameStatus) {
            friendStatus.text = stringFromRes(gameStatus.description)
            friendStatus.setVisibleIf()
        } else {
            friendStatus.setGoneIf()
        }
    }

    private fun bindLeagueTierAndDivision() {
        if (viewModel.tierAndDivision == RankedLeagueTierDivision.NONE) {
            leagueTierAndDivisionIcon.setGone()
            leagueTierAndDivisionIconText.setGone()
        } else {
            leagueTierAndDivisionIcon.setImageDrawable(drawableFromRes(viewModel.tierAndDivision.icon))
            leagueTierAndDivisionIconText.text = viewModel.tierAndDivision.descriptiveName
            leagueTierAndDivisionIcon.setVisibleIf()
            leagueTierAndDivisionIconText.setVisibleIf()
        }
    }

    private fun bindPresenceMode() {
        if (viewModel.presenceMode == PresenceMode.OFFLINE) {
            presenceMode.setGone()
        } else {
            presenceMode.imageTintList = ColorStateList.valueOf(colorFromRes(viewModel.presenceMode.color))
            presenceMode.setVisibleIf()
        }
    }

    override fun onRecycle() {
        collapse()
        friendSummonerIcon.colorFilter = null
        timeStampDisposable?.dispose()
    }

    override fun expand() {
        if (collapsableState != Collapsable.EXPANDED) {
            container.setVisible()
            collapsableState = Collapsable.EXPANDED
        }
    }

    override fun collapse() {
        if (collapsableState != Collapsable.COLLAPSED) {
            container.setGone()
            collapsableState = Collapsable.COLLAPSED
        }
    }

    private fun resumeTimeStampUpdated() {
        val pd = this.viewModel.playingData
        if (pd != null) {
            bindPlayingData(true, pd)
        }
        bindPlayingImage()
    }

    private fun stopTimeStampUpdated() {
        timeStampDisposable?.dispose()
    }
}