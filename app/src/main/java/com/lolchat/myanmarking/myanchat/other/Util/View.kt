package com.lolchat.myanmarking.myanchat.other.Util

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View

inline fun Context.drawableFromRes(drw: Int) = getDrawable(drw)
inline fun Activity.drawableFromRes(drw: Int) = getDrawable(drw)
inline fun View.drawableFromRes(drw: Int) = context.getDrawable(drw)
inline fun Fragment.drawableFromRes(drw: Int) = context.getDrawable(drw)