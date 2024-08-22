package com.bristot.lockthings.util.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<T>.observeAction(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner) { observer(it) }
}

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, EventObserver(observer))
}