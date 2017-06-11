package com.lolchat.myanmarking.myanchat.other.util

import com.squareup.moshi.Moshi
import io.reactivex.disposables.Disposable

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T> Moshi.toJson(v: T): String{
    return adapter(T::class.java).toJson(v)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T>Moshi.fromJson(value: String): T{
    return adapter(T::class.java).fromJson(value)
}

inline fun Disposable?.isNotDisposed() = this != null && !this.isDisposed