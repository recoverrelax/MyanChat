package com.lolchat.myanmarking.myanchat.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsRiotApi
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsUser
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomPersistentDb
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class StorageModule {

    @Provides
    @MyAppScope
    fun providePreferences(appContext: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    @Provides
    @MyAppScope
    fun provideUserPrefs(@Named("SharedPrefs") moshi: Moshi, pref: SharedPreferences): PrefsUser{
        return PrefsUser(pref, moshi)
    }

    @Provides
    @MyAppScope
    fun providePrefsRiotApi(@Named("SharedPrefs") moshi: Moshi, pref: SharedPreferences): PrefsRiotApi{
        return PrefsRiotApi(pref, moshi)
    }

    @Provides
    @MyAppScope
    fun providesRealmInteractor(db: RoomPersistentDb): RoomInteractor {
        return RoomInteractor(db)
    }

    @Provides
    @MyAppScope
    fun providesRoomDb(context: Application): RoomPersistentDb
            = RoomPersistentDb.createPersistentDatabase(context)
}