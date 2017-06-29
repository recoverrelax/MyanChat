package com.lolchat.myanmarking.myanchat.di.component

import android.app.Activity
import com.lolchat.myanmarking.myanchat.di.module.BaseActivityModule
import com.lolchat.myanmarking.myanchat.di.scopes.ActivityScope
import com.lolchat.myanmarking.myanchat.ui.activity.MainActivity
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = arrayOf(MainActivityComponent.Module::class))
interface MainActivityComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()

    @dagger.Module(includes = arrayOf(BaseActivityModule::class))
    class Module {
        @ActivityScope
        @Provides
        fun provideActivity(activity: MainActivity): Activity {
            return activity
        }
    }
}
