package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.lolchat.myanmarking.myanchat.R
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.dimen
import org.jetbrains.anko.dip
import timber.log.Timber


class FriendListAvatarBehavior : CoordinatorLayout.Behavior<CircleImageView> {

    private val NO_VALUE = -1
    private var statusBarHeight: Int = NO_VALUE
    private var toolbarHeight: Int = NO_VALUE
    private var appBarHeight: Int = NO_VALUE
    private var toolbarVerticalMarginToAvatar: Int = NO_VALUE
    private var avatarInitialWidth: Int = NO_VALUE
    private var avatarWidthReduction: Int = NO_VALUE
    private var avatarInitialMarginStart: Int = NO_VALUE
    private var avatarMarginAddiction: Int = NO_VALUE
    private var avatarYReduction: Int = NO_VALUE

    private var initedDimens: Boolean = false

    constructor()
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: CircleImageView, dependency: View): Boolean {
        if (!initedDimens) {
            initStatusBarSize(parent)
        }
        return dependency is AppBarLayout
    }

    private fun initStatusBarSize(parent: CoordinatorLayout) {
        val context = parent.context

        // statusBar
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        } else {
            statusBarHeight = context.dip(25)
        }

        // toolbar
        val tv = TypedValue()
        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            toolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
        } else {
            toolbarHeight = context.dip(56)
        }

        appBarHeight = context.dimen(R.dimen.friend_match_appbar_height)
        toolbarVerticalMarginToAvatar = context.dip(5)
        avatarInitialWidth = parent.context.dimen(R.dimen.friend_match_avatar_wh)
        avatarWidthReduction = avatarInitialWidth - (toolbarHeight - 2 * toolbarVerticalMarginToAvatar)
        avatarInitialMarginStart = context.dimen(R.dimen.friend_match_avatar_original_margin_start)
        avatarMarginAddiction = context.dip(50)
        avatarYReduction = (avatarInitialWidth / 2) - toolbarVerticalMarginToAvatar * 2

        initedDimens = true
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: CircleImageView, dependency: View): Boolean {
        if (child is CircleImageView && child.id == R.id.friendSummonerIcon) {
            val offset = Math.abs(dependency.y)
            val maxOffset = appBarHeight - statusBarHeight - toolbarHeight

            val scaleFactor = normalize(offset, 0f, maxOffset.toFloat())

            val lp = child.layoutParams as CoordinatorLayout.LayoutParams
            val d = Math.round(avatarInitialWidth - (scaleFactor * avatarWidthReduction))
            lp.width = d
            lp.height = d
            lp.marginStart = Math.round(avatarInitialMarginStart + (scaleFactor * avatarMarginAddiction))
            child.layoutParams = lp

            val yMin = (statusBarHeight + toolbarVerticalMarginToAvatar / 2).toFloat()
            val y = Math.max((dependency.y + dependency.height - (child.width / 2)) - (scaleFactor * avatarYReduction), yMin)
            child.y = y
            return true
        }
        return false
    }

    // normalize values to a range
    fun normalize(value: Float, min: Float, max: Float): Float
            = (value - min) / (max - min)
}
