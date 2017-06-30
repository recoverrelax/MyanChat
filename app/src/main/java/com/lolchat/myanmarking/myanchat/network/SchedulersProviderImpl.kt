package com.lolchat.myanmarking.myanchat.network

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object SchedulersProviderImpl: SchedulersProvider {
    override val ui: Scheduler
            = AndroidSchedulers.mainThread()

    override val io: Scheduler
            = Schedulers.io()
    override val computation: Scheduler
            = Schedulers.computation()
}
interface SchedulersProvider{
    val ui: Scheduler
    val io: Scheduler
    val computation: Scheduler
}