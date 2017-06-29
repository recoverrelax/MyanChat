package com.lolchat.myanmarking.myanchat.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.base.mvp.BaseViewModel
import com.lolchat.myanmarking.myanchat.base.mvp.BaseViewStates
import com.lolchat.myanmarking.myanchat.di.component.MyAppComponent
import com.lolchat.myanmarking.myanchat.other.util.inDebug
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<M : BaseViewModel<S>, S : BaseViewStates> : Fragment() {

    @Inject protected lateinit var viewModel: M
    abstract val layoutRes: Int

    private var stateDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        val view = inflater.inflate(layoutRes, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        stateDisposable = viewModel.viewState
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            render(it)
                            inDebug { Timber.i("Rendering: ${it::class.java}") }
                        },
                        {
                            Timber.e(it)
                            throw it
                        }
                )
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onPause() {
        super.onPause()
        stateDisposable?.dispose()
    }

    abstract fun render(viewState: S)
}