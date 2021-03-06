package com.lolchat.myanmarking.myanchat.base.prefs

import android.content.SharedPreferences
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.other.util.fromJson
import com.lolchat.myanmarking.myanchat.other.util.toJson
import com.squareup.moshi.Moshi

abstract class BasePref(
        val pref: SharedPreferences,
        val moshi: Moshi
) {

    protected fun get(key: String, default: String): String = pref.getString(key, default)
    protected fun get(key: String, default: Int): Int = pref.getInt(key, default)
    protected fun get(key: String, default: Float): Float = pref.getFloat(key, default)
    protected fun get(key: String, default: Long): Long = pref.getLong(key, default)
    protected fun get(key: String, default: Boolean): Boolean = pref.getBoolean(key, default)

    protected fun put(key: String, value: String): Unit = pref.editor { putString(key, value) }
    protected fun put(key: String, value: Int): Unit = pref.editor { putInt(key, value) }
    protected fun put(key: String, value: Float): Unit = pref.editor { putFloat(key, value) }
    protected fun put(key: String, value: Long): Unit = pref.editor { putLong(key, value) }
    protected fun put(key: String, value: Boolean): Unit = pref.editor { putBoolean(key, value) }

    protected inline fun SharedPreferences.editor(editor: SharedPreferences.Editor.() -> Unit) {
        edit().apply { editor(this) }.apply()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    protected inline fun <reified T> getPojo(key: String, default: T): T {
        val prefs = pref
        val json = prefs.getString(key, EMPTY_STRING)
        return if(json != EMPTY_STRING) moshi.fromJson<T>(json) else default
    }

    protected inline fun <reified T> putPojo(key: String, value: T) {
        val prefs = pref
        prefs.edit().apply {
            val jsonValue = if(value == null) EMPTY_STRING else moshi.toJson<T>(value)
            putString(key, jsonValue)
        }.apply()
    }

    protected inline fun editMultiple(doStuff: () -> Unit) {
        val prefs = pref
        prefs.edit().apply {
            doStuff()
        }.apply()
    }

    protected fun hasPreference(key: String) = pref.contains(key)
}