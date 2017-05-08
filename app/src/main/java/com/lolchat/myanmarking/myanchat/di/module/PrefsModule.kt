package com.lolchat.myanmarking.myanchat.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import com.lolchat.myanmarking.myanchat.io.prefs.PrefsUser
import dagger.Module
import dagger.Provides

@Module
class PrefsModule {

    @Provides
    @MyAppScope
    fun providePreferences(appContext: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    @Provides
    @MyAppScope
    fun provideUserPrefs(pref: SharedPreferences): PrefsUser{
        return PrefsUser(pref)
    }
}