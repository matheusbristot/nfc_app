package com.bristot.light.nfc.bridge

import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.util.Log
import com.bristot.light.nfc.model.NfcArgs
import com.bristot.light.nfc.model.NfcProperties


class NfcBridgeImpl(
    private val preSetTagId: String,
    private val context: Context,
    private val nfcArgs: NfcArgs = NfcArgs()
) : NfcBridge {

    private val adapter by lazy { NfcAdapter.getDefaultAdapter(context) }
    private var foundCallback: ((Boolean) -> Unit)? = null

    override fun getNfcAdapter(): NfcAdapter? {
        return adapter
    }

    override val nfcProperties: NfcProperties
        get() {
            return NfcProperties(
                isSupported = getNfcAdapter() != null,
                isEnabled = getNfcAdapter()?.isEnabled ?: false,
                isTagTech = false,
                nfcArgs = nfcArgs
            )
        }

    private var tag: Tag? = null

    override fun getCurrentTag(): Tag? {
        return tag
    }

    /*
    * Sample identifier BIP Card: 9CCf7051
    * */
    override fun onTagDiscovered(tag: Tag?) {
        this.tag = tag
        try {
            val tagFounded = tagIfFounded()
            foundCallback?.invoke(tagFounded)
        } catch (e: Exception) {
            foundCallback?.invoke(false)
        }
    }

    private fun tagIfFounded() = this.tag?.id?.toHexString()?.uppercase() == preSetTagId.uppercase()

    override fun tagDiscoveredCallback(isFound: (Boolean) -> Unit) {
        foundCallback = isFound
    }

    override fun onTagRemoved() {
        tag = null
    }


    private fun ByteArray.toHexString(): String {
        val hexChars = "0123456789ABCDEF"
        val result = StringBuilder(size * 2)

        map { byte ->
            val value = byte.toInt()
            val hexChar1 = hexChars[value shr 4 and 0x0F]
            val hexChar2 = hexChars[value and 0x0F]
            result.append(hexChar1)
            result.append(hexChar2)
        }

        return result.toString()
    }
}