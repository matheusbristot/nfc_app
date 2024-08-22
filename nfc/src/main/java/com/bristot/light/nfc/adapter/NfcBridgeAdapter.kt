package com.bristot.light.nfc.adapter

import com.bristot.light.nfc.model.NfcProperties

interface NfcBridgeAdapter {
    fun enable()
    fun disable()
    fun callbackForResultTagDiscovered(callback: (Boolean) -> Unit)
    fun cantReader(callback: (NfcProperties) -> Unit)
    fun enableForeGround()
}
