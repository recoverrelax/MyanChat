package com.lolchat.myanmarking.myanchat.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent

abstract class BaseFragment: Fragment(){

    abstract fun createComponent(app: MyAppComponent)
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createComponent(MyApp.INSTANCE.appComponent)
    }
}