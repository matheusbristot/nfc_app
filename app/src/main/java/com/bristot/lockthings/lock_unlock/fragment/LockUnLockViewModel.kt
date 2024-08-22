package com.bristot.lockthings.lock_unlock.fragment

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bristot.light.nfc.model.NfcProperties
import com.bristot.lockthings.util.livedata.Event
import com.bristot.lockthings.util.resources.StringProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LockUnLockViewModel(
    private val tagId: String,
    private val stringProvider: StringProvider
) : ViewModel(), DefaultLifecycleObserver {

    val state: LiveData<Event<LockUnlockState>> get() = _state
    private val _state = MutableLiveData<Event<LockUnlockState>>()

    private var action: String? = null
    private var canExpire: Boolean = true
    private var job: Job? = null

    fun onSendActionLock() {
        val state = LockUnlockState.Lock()
        action = state.action
        _state.value = Event(state)
        ticker()
    }

    fun onSendActionUnLock() {
        val state = LockUnlockState.UnLock()
        action = state.action
        _state.value = Event(state)
        ticker()
    }

    fun onCantReader(nfcProperties: NfcProperties) {
        if (!nfcProperties.isSupported) {
            val message = stringProvider.deviceNotSupportNFCMessage
            _state.postValue(Event(LockUnlockState.NotSupport(message)))
        } else if (!nfcProperties.isEnabled) {
            val message = stringProvider.turnOnNFCMessage
            _state.postValue(Event(LockUnlockState.TurnOnNFC(message)))
        }
    }

    fun onDiscoveredResult(isEquals: Boolean) {
        cancelJob()
        canExpire = false
        val state = if (!isEquals) {
            val message = stringProvider.tagNotEqualsMessage(tagId.uppercase())
            LockUnlockState.WrongTag(message)
        } else {
            val message = stringProvider.tagSuccessMessage(action.orEmpty())
            LockUnlockState.SuccessAction(message)
        }
        _state.postValue(Event(state))
    }

    private fun ticker() {
        val times = 30
        canExpire = true
        cancelJob()
        job = viewModelScope.launch(Dispatchers.IO) {
            repeat(times) { second ->
                _state.postValue(Event(LockUnlockState.Ticker("${times - second}s")))
                delay(1000L)
            }
            if (canExpire) {
                val message = stringProvider.tagTimeoutMessage(action.orEmpty())
                _state.postValue(Event(LockUnlockState.Expired(message)))
            }
        }
    }

    private fun cancelJob() {
        job?.isActive?.run {
            if (this) {
                job?.cancel()
            }
        }
    }
}