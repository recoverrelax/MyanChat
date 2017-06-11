package com.lolchat.myanmarking.myanchat.other.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Looper

fun Context.getSystemRes(res: Int): Drawable {
    val attrs = intArrayOf(res)

    // Obtain the styled attributes. 'themedContext' is a context with a
    // theme, typically the current Activity (i.e. 'this')
    val ta = obtainStyledAttributes(attrs)

    // Now get the value of the 'listItemBackground' attribute that was
    // set in the theme used in 'themedContext'. The parameter is the index
    // of the attribute in the 'attrs' array. The returned Drawable
    // is what you are after
    val drawableFromTheme = ta.getDrawable(0 /* index */)

    // Finally free resources used by TypedArray
    ta.recycle()

    // setBackground(Drawable) requires API LEVEL 16,
    // otherwise you have to use deprecated setBackgroundDrawable(Drawable) method.
    return drawableFromTheme
}

fun assertMainThread(){
    if(Looper.getMainLooper() != Looper.myLooper()){
        throw IllegalStateException("This should be called on the main thread!")
    }
}

fun assertNotMainThread(){
    if(Looper.getMainLooper() == Looper.myLooper()){
        throw IllegalStateException("This should NOT be called on the main thread!")
    }
}