package com.bristot.lockthings.lock_unlock.bottomsheet

import android.text.Spanned
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bristot.lockthings.util.livedata.Event
import com.bristot.lockthings.util.resources.StringProvider

class WaitConnectTagBottomViewModel(
    private val tagId: String,
    private val action: String,
    private val stringProvider: StringProvider
) : ViewModel(), DefaultLifecycleObserver {

    val message: LiveData<Event<Spanned>> get() = _message
    private val _message = MutableLiveData<Event<Spanned>>()

    fun onWait() {
        val message = stringProvider.waitTagMessage(tagId.uppercase(), action)
        _message.value = Event(message)
    }
}