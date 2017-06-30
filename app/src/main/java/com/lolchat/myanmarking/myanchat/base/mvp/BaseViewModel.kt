package com.lolchat.myanmarking.myanchat.base.mvp

import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.lolchat.myanmarking.myanchat.network.SchedulersProvider
import com.lolchat.myanmarking.myanchat.network.SchedulersProviderImpl
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<S: BaseViewStates>: ViewModel() {

    private val lazyCompositeSubscription = lazy { CompositeDisposable() }
    protected val subscriptions: CompositeDisposable by lazyCompositeSubscription

    val schedulersProvider: SchedulersProvider = SchedulersProviderImpl

    val viewState: PublishRelay<S> = PublishRelay.create()

    override fun onCleared() {
        super.onCleared()
        if (lazyCompositeSubscription.isInitialized()) {
            subscriptions.clear()
        }
    }

    open fun postEvent(event: S){
        viewState.accept(event)
    }
}