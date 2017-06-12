package com.lolchat.myanmarking.myanchat.base.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<M : BaseViewModel<S>, S: BaseViewStates> : AppCompatActivity() {

    @Inject protected lateinit var viewModel: M
    abstract val layoutRes: Int

    private var stateDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        injectComponent(MyApp.INSTANCE.appComponent)
    }

    override fun onResume() {
        super.onResume()
        stateDisposable = viewModel.viewState.subscribe(
                {render(it)},
                {
                    Timber.e(it)
                    throw it
                }
        )
    }

    override fun onPause() {
        super.onPause()
        stateDisposable?.dispose()
    }

    abstract fun injectComponent(appComponent: MyAppComponent)
    abstract fun render(viewState: S)
}