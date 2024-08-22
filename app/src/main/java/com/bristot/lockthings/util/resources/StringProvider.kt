package com.bristot.lockthings.util.resources

import android.text.Spanned

interface StringProvider {
    val registerLockSuccessMessage: String
    val registerLockErrorMessage: String
    val deviceNotSupportNFCMessage: String
    val turnOnNFCMessage: String

    fun waitTagMessage(tagId: String, action: String): Spanned
    fun tagNotEqualsMessage(tagId: String): Spanned
    fun tagTimeoutMessage(action: String): Spanned
    fun tagSuccessMessage(action: String): Spanned
}
