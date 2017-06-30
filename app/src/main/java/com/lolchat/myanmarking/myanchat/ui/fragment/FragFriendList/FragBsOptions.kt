package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.BaseBottomSheet
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.other.Navigator
import kotlinx.android.synthetic.main.frag_friend_bs_options.view.*

/*
BsFitnessItemDetailed().show(act.supportFragmentManager,
BsFitnessItemDetailed.Builder(
title, subTitle, description
))
*/

class FragBsOptions : BaseBottomSheet() {
    override val cancelable: Boolean = true

    companion object {
        const val TAG = "FragBsOptions.TAG"
        const val BUILDER = "FragBsOptions.BUILDER"
    }

    fun newInstance(manager: FragmentManager, friendName: String) {
        val bundle = Bundle().apply {
            putString(BUILDER, friendName)
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
        view.matchContainer.setOnClickListener {
            val friendName = arguments.getString(BUILDER, EMPTY_STRING)
            if (friendName != EMPTY_STRING) {
                val act = activity
                if (act is FragBsOptionsCb) {
                    act.onFriendMatchListClicked(friendName)
                }
            } else {
                throw IllegalStateException()
            }
            this.dismiss()
        }
    }
}

interface FragBsOptionsCb {
    fun onFriendMatchListClicked(friendName: String)
}