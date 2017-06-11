package com.lolchat.myanmarking.myanchat.base

import android.os.Bundle
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.base.mvp.BasePresenter
import com.lolchat.myanmarking.myanchat.base.mvp.IMvpView
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent

abstract class BaseMvpFragment<V: IMvpView>: BaseFragment(){
    abstract protected val presenter: BasePresenter<V>
    abstract fun createComponent(app: MyAppComponent)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createComponent(MyApp.INSTANCE.appComponent)

        if(this !is IMvpView)
            throw IllegalStateException("This fragment should implement ${IMvpView::class.java}")

        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}