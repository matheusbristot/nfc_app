package com.bristot.lockthings.lock_unlock.fragment

import android.text.Spanned

sealed interface LockUnlockState {
    data class Lock(val action: String = LOCK) : LockUnlockState
    data class UnLock(val action: String = UNLOCK) : LockUnlockState
    data class WrongTag(val message: Spanned) : LockUnlockState
    data class SuccessAction(val message: Spanned) : LockUnlockState
    data class Expired(val message: Spanned) : LockUnlockState
    data class Ticker(val message: String) : LockUnlockState
    data class NotSupport(val message: String) : LockUnlockState
    data class TurnOnNFC(val message: String) : LockUnlockState

    private companion object {
        const val LOCK = "fechar"
        const val UNLOCK = "abrir"
    }
}
