package com.lolchat.myanmarking.myanchat.base.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class SquareImageView: ImageView{

    @JvmOverloads
    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = measuredHeight
        setMeasuredDimension(height, height)
    }
}