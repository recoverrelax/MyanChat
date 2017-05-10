package com.lolchat.myanmarking.myanchat.other.util

import android.app.Activity
import android.content.Context
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

inline fun Context.stringFromRes(res: Int) = getString(res)
inline fun Activity.stringFromRes(res: Int) = resources.getString(res)
inline fun View.stringFromRes(res: Int) = context.getString(res)
inline fun Fragment.stringFromRes(res: Int) = context.getString(res)

inline fun View.setGone(){
    this.visibility = View.GONE
}

inline fun View.setGoneIf(){
    if(this.visibility != View.GONE){
        this.visibility = View.GONE
    }
}

inline fun View.setVisible(){
    this.visibility = View.VISIBLE
}

inline fun View.setVisibleIf(){
    if(this.visibility != View.VISIBLE){
        this.visibility = View.VISIBLE
    }
}