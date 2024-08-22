package com.bristot.light.nfc.model

import android.nfc.NfcAdapter
import android.os.Bundle

data class NfcArgs(
    val delay: Int = 250,
    val flag: Int = NfcAdapter.FLAG_READER_NFC_A or
            NfcAdapter.FLAG_READER_NFC_B or
            NfcAdapter.FLAG_READER_NFC_BARCODE or
            NfcAdapter.FLAG_READER_NFC_F or
            NfcAdapter.FLAG_READER_NFC_V,
    val extras: Bundle = Bundle().apply {
        putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, delay)
    }
)
