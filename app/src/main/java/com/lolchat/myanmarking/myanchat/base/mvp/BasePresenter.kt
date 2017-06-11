package com.lolchat.myanmarking.myanchat.base.mvp

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T : IMvpView> : Presenter<T> {

    private val lazyCompositeSubscription = lazy { CompositeDisposable() }
    protected val subscriptions: CompositeDisposable by lazyCompositeSubscription

    lateinit var view: T
    private set

    private var isViewAttached: Boolean = false

    override fun attachView(mvpView: T) {
        view = mvpView
        isViewAttached = true
    }

    override fun detachView() {
        if (lazyCompositeSubscription.isInitialized()) {
            subscriptions.clear()
        }
        isViewAttached = false
    }

    override fun isViewAttached() = isViewAttached
}