package com.bristot.lockthings.register

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bristot.lockthings.util.livedata.Event
import com.bristot.lockthings.util.resources.StringProvider


class RegisterLockIdViewModel(
    private val stringProvider: StringProvider
) : ViewModel(), DefaultLifecycleObserver {

    val isIdentifierValid: LiveData<Event<LockIdRegisterState>> get() = _isIdentifierValid
    private val _isIdentifierValid = MutableLiveData<Event<LockIdRegisterState>>()

    fun onCheckLockIdentifier(id: String) {
        // Exemplo de regra para validar se o ID inserido é valido
        val isValid = id.isNotEmpty() && id.length >= 5
        val state = if (isValid) {
            LockIdRegisterState.CreatedLock(
                id = id.trim(),
                message = stringProvider.registerLockSuccessMessage
            )
        } else {
            LockIdRegisterState.Error(errorMessage = stringProvider.registerLockErrorMessage)
        }
        _isIdentifierValid.value = Event(state)
    }
}
