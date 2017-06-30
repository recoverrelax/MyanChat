package com.lolchat.myanmarking.myanchat.base.view

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.View

class MyNestedScrollView: NestedScrollView {

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    override fun onNestedFling(target: View?, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }
}