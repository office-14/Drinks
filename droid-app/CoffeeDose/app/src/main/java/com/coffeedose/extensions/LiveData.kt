package com.coffeedose.extensions

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveData(default: T? = null): MutableLiveData<T> {
    val data = MutableLiveData<T>()

    if (default != null)
        data.value = default
    return  data
}