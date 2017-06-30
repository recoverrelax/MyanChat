package com.lolchat.myanmarking.myanchat.other

import android.support.v7.app.AppCompatActivity
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendList
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList.FragFriendMatchList

class Navigator{

    fun startWithFragFriendList(act: AppCompatActivity, containerId: Int){
        val frag = FragFriendList.newInstance()

        act.supportFragmentManager.beginTransaction()
                .add(containerId, frag).commit()
    }

    fun goToFriendMatchList(act: AppCompatActivity, containerId: Int, friendName: String) {
        val frag = FragFriendMatchList.newInstance(friendName)

        act.supportFragmentManager.beginTransaction()
                .replace(containerId, frag)
                .addToBackStack(FragFriendMatchList.TAG)
                .commit()
    }
}