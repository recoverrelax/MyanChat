package com.lolchat.myanmarking.myanchat.di.module

import android.app.Application
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import dagger.Module
import dagger.Provides

@Module
class MyAppModule(
        val context: MyApp
) {

    @Provides
    @MyAppScope
    fun providesAppContext(): Application {
        return context
    }
}