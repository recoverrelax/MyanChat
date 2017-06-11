package com.lolchat.myanmarking.myanchat.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lolchat.myanmarking.myanchat.R
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendList

class MainActivity: AppCompatActivity(){

    companion object{
        fun startActivity(context: Context){
            context.startActivity(
                    Intent(context, MainActivity::class.java)
            )
        }
    }

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