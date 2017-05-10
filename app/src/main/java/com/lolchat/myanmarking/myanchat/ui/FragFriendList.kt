package com.lolchat.myanmarking.myanchat.ui

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.BaseFragment
import com.lolchat.myanmarking.myanchat.di.component.DaggerFragFriendListComponent
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent
import com.lolchat.myanmarking.myanchat.di.module.FragFriendListModule
import com.lolchat.myanmarking.myanchat.io.interfaces.IFriendChangeListener
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.model.xmpp.Friend
import com.lolchat.myanmarking.myanchat.other.recyclerview.VerticalDividerRvDecorator
import com.lolchat.myanmarking.myanchat.ui.adapter.FragFriendListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.frag_friend_list.*
import timber.log.Timber
import javax.inject.Inject

class FragFriendList: BaseFragment() {
    override fun getLayoutRes(): Int = R.layout.frag_friend_list

    companion object{
        fun newInstance(): FragFriendList{
            return FragFriendList()
        }
    }

    @Inject lateinit var xmppManager: IXmppManager
    @Inject lateinit var myAdapter: FragFriendListAdapter
    @Inject lateinit var myLayoutManager: RecyclerView.LayoutManager

    override fun createComponent(app: MyAppComponent){
        DaggerFragFriendListComponent.builder()
                .myAppComponent(app)
                .fragFriendListModule(FragFriendListModule(activity))
                .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        xmppManager.removeOnFriendChangeListener(myAdapter)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        xmppManager.addOnFriendChangeListener(myAdapter)

        friendListRecyclerView.run {
            layoutManager = myLayoutManager
            addItemDecoration(VerticalDividerRvDecorator(activity))
            adapter = myAdapter
        }

        xmppManager.getFriendListName()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            myAdapter.setAdapterItems(it)
                        },
                        { Timber.e(it) }
                )
    }
}