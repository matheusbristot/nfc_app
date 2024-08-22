package com.bristot.lockthings.register

import java.io.Serializable

sealed class LockIdRegisterState : Serializable {

    data class CreatedLock(
        val id: String,
        val message: String
    ) : LockIdRegisterState()

    data class Error(
        val errorMessage: String
    ) : LockIdRegisterState()
}

