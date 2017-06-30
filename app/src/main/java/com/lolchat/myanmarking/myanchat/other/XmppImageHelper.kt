package com.lolchat.myanmarking.myanchat.other

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lolchat.myanmarking.myanchat.io.Riot.RiotApiConst
import com.lolchat.myanmarking.myanchat.io.model.xmpp.SkinInfo
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.other.util.drawableFromRes
import timber.log.Timber

class XmppImageHelperImpl(
        val riotApiConst: RiotApiConst
) {

    private val greyColorFilter: ColorMatrixColorFilter
            = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })

    fun loadProfileIconUrl(context: Context, profileIconId: String, view: ImageView, isOnline: Boolean) {
        val imageUrl = riotApiConst.getProfileIconUrl(profileIconId)
        val defaultDrawable = riotApiConst.DDRAGON_DEFAULT_PROFILE_IC

        if (profileIconId != EMPTY_STRING) {
            Glide.with(context)
                    .load(imageUrl)
                    .error(defaultDrawable)
                    .into(view)
        } else {
            view.setImageDrawable(context.drawableFromRes(defaultDrawable))
            view.colorFilter = if (isOnline) null else greyColorFilter
        }
    }

    fun loadSkin(context: Context, skinInfo: SkinInfo, view: ImageView) {
        val defaultDrawable = riotApiConst.DDRAGON_DEFAULT_PROFILE_IC

        val imageUrl = riotApiConst.getSkinVariantUrl(skinInfo.getComposedName())
        Timber.i("Trying to load skin: $imageUrl")

        Glide.with(context)
                .load(imageUrl)
                .error(defaultDrawable)
                .into(view)
    }
}