package com.lolchat.myanmarking.myanchat.base.mvp

interface IBaseViewModel<in V: IMvpView>{
    fun attachView(mvpView: V)
}