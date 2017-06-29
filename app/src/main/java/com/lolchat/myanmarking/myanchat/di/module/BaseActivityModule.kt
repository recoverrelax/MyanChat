package com.lolchat.myanmarking.myanchat.di.module

import android.app.Activity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.lolchat.myanmarking.myanchat.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(FragmentBindModule::class))
class BaseActivityModule {
    @ActivityScope
    @Provides
    fun provideAppCompatActivity(activity: Activity): AppCompatActivity {
        return activity as AppCompatActivity
    }

    @ActivityScope
    @Provides
    fun provideFragmentManager(activity: AppCompatActivity): FragmentManager {
        return activity.supportFragmentManager
    }
}
