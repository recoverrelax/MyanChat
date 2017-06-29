package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.BaseBottomSheet

/*
BsFitnessItemDetailed().show(act.supportFragmentManager,
BsFitnessItemDetailed.Builder(
title, subTitle, description
))
*/

class FragBsOptions: BaseBottomSheet() {
    override val cancelable: Boolean = true

    companion object {
        const val TAG = "FragBsOptions.TAG"
    }

    /*data class Builder(
            val title: String,
            val subtitle: String,
            val content: String
    ) : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<Builder> = object : Parcelable.Creator<Builder> {
                override fun createFromParcel(source: Parcel): Builder = Builder(source)
                override fun newArray(size: Int): Array<Builder?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString())

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(title)
            dest?.writeString(subtitle)
            dest?.writeString(content)
        }
    }*/

    fun show(manager: FragmentManager) {
        val bundle = Bundle().apply {
//            putParcelable(BUNDLE_BUILDER, builder)
        }
        this.arguments = bundle
        show(manager, TAG)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_friend_bs_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
    }
}