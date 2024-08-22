package com.bristot.light.nfc.model

data class NfcProperties(
    val isSupported: Boolean,
    val isEnabled: Boolean,
    var isTagTech: Boolean,
    val nfcArgs: NfcArgs
) {
    fun canReaderOrRecorder(): Boolean = isSupported && isEnabled
}
