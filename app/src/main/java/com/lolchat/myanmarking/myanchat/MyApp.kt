package com.lolchat.myanmarking.myanchat

import android.app.Activity
import android.app.Application
import timber.log.Timber
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.lolchat.myanmarking.myanchat.di.component.DaggerMyAppComponent
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent
import com.lolchat.myanmarking.myanchat.di.module.NetworkModule
import com.lolchat.myanmarking.myanchat.di.module.StorageModule
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsUser
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApp : Application(), HasActivityInjector {

    companion object {
        lateinit var INSTANCE: MyApp
            private set
        val DEBUG: Boolean = BuildConfig.DEBUG
    }

    lateinit var appComponent: MyAppComponent
        private set

    @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var userPref: PrefsUser
    @Inject lateinit var xmppManager: XmppManager

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        // dagger init
        appComponent = DaggerMyAppComponent.builder()
                .myAppModule(MyAppComponent.MyAppModule(this))
                .storageModule(StorageModule())
                .networkModule(NetworkModule())
                .build().apply { inject(this@MyApp) }

        dummyInitPrefs()

        // init Timber
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // innit StrictMode
        if (DEBUG) {
            Stetho.initializeWithDefaults(this)

            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog()
                    .build())

            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyDeath()
                    .penaltyLog().build())
        }

    }

    override fun activityInjector(): AndroidInjector<Activity>
            = dispatchingActivityInjector

    private fun dummyInitPrefs() {
        if (!userPref.hasUsernamePref()) {

        }
    }
}