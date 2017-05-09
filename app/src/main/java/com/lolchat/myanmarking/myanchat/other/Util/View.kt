package com.lolchat.myanmarking.myanchat.other.Util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View

inline fun Context.drawableFromRes(drw: Int) = getDrawable(drw)
inline fun Activity.drawableFromRes(drw: Int) = getDrawable(drw)
inline fun View.drawableFromRes(drw: Int) = context.getDrawable(drw)
inline fun Fragment.drawableFromRes(drw: Int) = context.getDrawable(drw)

inline fun Context.colorFromRes(color: Int) = ContextCompat.getColor(this, color)
inline fun Activity.colorFromRes(color: Int) = ContextCompat.getColor(this, color)
inline fun View.colorFromRes(color: Int) = ContextCompat.getColor(context, color)
inline fun Fragment.colorFromRes(color: Int) = ContextCompat.getColor(context, color)