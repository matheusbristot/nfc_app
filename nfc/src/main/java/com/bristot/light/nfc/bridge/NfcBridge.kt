package com.bristot.light.nfc.bridge

import android.nfc.NfcAdapter
import android.nfc.Tag
import com.bristot.light.nfc.model.NfcProperties

internal interface NfcBridge : NfcAdapter.ReaderCallback, NfcAdapter.OnTagRemovedListener {
    fun getNfcAdapter(): NfcAdapter?
    val nfcProperties: NfcProperties
    fun getCurrentTag(): Tag?
    fun tagDiscoveredCallback(isFound: (Boolean) -> Unit)
}
