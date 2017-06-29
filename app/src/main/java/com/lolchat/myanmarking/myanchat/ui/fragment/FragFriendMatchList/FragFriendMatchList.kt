package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList

import android.os.Bundle
import android.view.View
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.BaseFragment
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.IFragFriendListView

class FragFriendMatchList : BaseFragment<FragFriendMatchListViewModel, FragFriendMatchListViewStates>(), IFragFriendListView {

    override val layoutRes: Int = R.layout.frag_friend_match_list
    companion object {
        fun newInstance(): FragFriendMatchList
                = FragFriendMatchList()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun render(viewState: FragFriendMatchListViewStates) {

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}