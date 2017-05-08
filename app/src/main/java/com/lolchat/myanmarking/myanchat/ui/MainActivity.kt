package com.lolchat.myanmarking.myanchat.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lolchat.myanmarking.myanchat.R

class MainActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        if(savedInstanceState == null){
            val frag = FragFriendList.newInstance()
            // Add the fragment to the 'fragment_container' FrameLayout
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, frag).commit()
        }
    }
}