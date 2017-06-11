package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.BaseMvpFragment
import com.lolchat.myanmarking.myanchat.di.component.DaggerFragFriendListComponent
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent
import com.lolchat.myanmarking.myanchat.di.module.FragFriendListModule
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.other.recyclerview.VerticalSpaceRvDecorator
import com.lolchat.myanmarking.myanchat.ui.adapter.FragFriendListAdapter
import kotlinx.android.synthetic.main.frag_friend_list.*
import org.jetbrains.anko.dip
import javax.inject.Inject

class FragFriendList : BaseMvpFragment<IFragFriendListView>(), IFragFriendListView {

    override fun getLayoutRes(): Int = R.layout.frag_friend_list

    companion object {
        fun newInstance(): FragFriendList {
            return FragFriendList()
        }
    }

    @Inject override lateinit var presenter: FragFriendListPresenter
    @Inject lateinit var xmppManager: IXmppManager
    @Inject lateinit var myAdapter: FragFriendListAdapter
    @Inject lateinit var myLayoutManager: RecyclerView.LayoutManager

    override fun createComponent(app: MyAppComponent) {
        DaggerFragFriendListComponent.builder()
                .myAppComponent(app)
                .fragFriendListModule(FragFriendListModule(this))
                .build().inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.addOnFriendChangeListener()

        friendListRecyclerView.run {
            layoutManager = myLayoutManager
            addItemDecoration(VerticalSpaceRvDecorator(dip(10)))
            adapter = myAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.removeOnFriendChangeListener()
    }
}