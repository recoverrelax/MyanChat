package com.lolchat.myanmarking.myanchat.base.mvp

interface Presenter<in V: IMvpView> {

    fun attachView(mvpView: V)
    fun detachView()
    fun isViewAttached(): Boolean
    fun release()
}