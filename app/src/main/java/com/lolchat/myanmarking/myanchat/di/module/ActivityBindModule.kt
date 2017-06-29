package com.lolchat.myanmarking.myanchat.di.module

import android.app.Activity
import com.lolchat.myanmarking.myanchat.di.component.MainActivityComponent
import com.lolchat.myanmarking.myanchat.ui.activity.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(
        subcomponents = arrayOf(MainActivityComponent::class)
)
abstract class ActivityBindModule{

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivityComponent.Builder): AndroidInjector.Factory<out Activity>
}