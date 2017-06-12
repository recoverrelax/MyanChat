package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.base.BaseFragment
import com.lolchat.myanmarking.myanchat.di.component.DaggerFragFriendListComponent
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent
import com.lolchat.myanmarking.myanchat.di.module.FragFriendListModule
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.other.recyclerview.VerticalSpaceRvDecorator
import com.lolchat.myanmarking.myanchat.other.util.setInvisible
import com.lolchat.myanmarking.myanchat.other.util.setVisible
import com.lolchat.myanmarking.myanchat.ui.adapter.FragFriendListAdapter
import kotlinx.android.synthetic.main.frag_friend_list.*
import org.jetbrains.anko.dip
import javax.inject.Inject

class FragFriendList : BaseFragment<FragFriendListViewModel, FragFriendListViewStates>(), IFragFriendListView {
    override val layoutRes: Int = R.layout.frag_friend_list
    companion object {
        fun newInstance(): FragFriendList
                = FragFriendList()
    }

    @Inject lateinit var xmppManager: IXmppManager
    @Inject lateinit var myAdapter: FragFriendListAdapter
    @Inject lateinit var myLayoutManager: RecyclerView.LayoutManager

    override fun injectComponent(appComponent: MyAppComponent) {
        DaggerFragFriendListComponent.builder()
                .myAppComponent(appComponent)
                .fragFriendListModule(FragFriendListModule(this))
                .build().inject(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendListRecyclerView.run {
            layoutManager = myLayoutManager
            addItemDecoration(VerticalSpaceRvDecorator(dip(10)))
            adapter = myAdapter
        }
    }

    override fun render(viewState: FragFriendListViewStates) {
        if(viewState.isLoading){
            progressBar.setVisible()
            friendListRecyclerView.setInvisible()
        }else{
            progressBar.setInvisible()
            friendListRecyclerView.setVisible()
        }

        when(viewState){
            is FragFriendListViewStates.Loading -> {}
            is FragFriendListViewStates.OnFreshData -> { myAdapter.onFreshData(viewState.list) }
            is FragFriendListViewStates.OnChangedData -> { myAdapter.onDataChanged(viewState.list) }
            is FragFriendListViewStates.OnNoData -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}