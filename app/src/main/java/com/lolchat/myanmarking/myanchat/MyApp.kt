package com.lolchat.myanmarking.myanchat

import android.app.Application
import timber.log.Timber
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.lolchat.myanmarking.myanchat.di.component.DaggerMyAppComponent
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent
import com.lolchat.myanmarking.myanchat.di.module.MyAppModule
import com.lolchat.myanmarking.myanchat.di.module.NetworkModule
import com.lolchat.myanmarking.myanchat.di.module.StorageModule
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsUser
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
import javax.inject.Inject

class MyApp : Application() {

    companion object {
        lateinit var INSTANCE: MyApp
            private set
        val DEBUG: Boolean = BuildConfig.DEBUG
    }

    lateinit var appComponent: MyAppComponent
        private set

    @Inject lateinit var userPref: PrefsUser
    @Inject lateinit var xmppManager: XmppManager

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

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

        // dagger init
        appComponent = DaggerMyAppComponent.builder()
                .myAppModule(MyAppModule(this))
                .storageModule(StorageModule())
                .networkModule(NetworkModule())
                .build().apply { inject(this@MyApp) }

        dummyInitPrefs()
    }

    private fun dummyInitPrefs() {
        if (!userPref.hasUsernamePref()) {
            userPref.username = "
            userPref.password = ""
        }
    }
}