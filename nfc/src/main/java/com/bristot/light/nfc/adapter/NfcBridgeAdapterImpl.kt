package com.bristot.light.nfc.adapter

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcBarcode
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import com.bristot.light.nfc.bridge.NfcBridge
import com.bristot.light.nfc.model.NfcProperties
import com.bristot.light.nfc.scope.NfcActivityScopeAccess

internal class NfcBridgeAdapterImpl(
    private val nfcBridge: NfcBridge,
    private val nfcActivityScopeAccess: NfcActivityScopeAccess
) : NfcBridgeAdapter {

    private var cantReaderCallback: ((NfcProperties) -> Unit)? = null

    override fun enable() {
        val activity = nfcActivityScopeAccess.getActivity() ?: return
        val args = nfcBridge.nfcProperties.nfcArgs
        with(nfcBridge) {
            if (nfcProperties.canReaderOrRecorder()) {
                getNfcAdapter()?.enableReaderMode(
                    activity,
                    this,
                    args.flag,
                    args.extras
                )
            } else {
                cantReaderCallback?.invoke(nfcProperties)
            }
        }
    }

    override fun cantReader(callback: (NfcProperties) -> Unit) {
        cantReaderCallback = callback
        cantReaderCallback?.invoke(nfcBridge.nfcProperties)
    }

    override fun callbackForResultTagDiscovered(callback: (Boolean) -> Unit) {
        nfcBridge.tagDiscoveredCallback(callback)
    }

    override fun disable() {
        val activity = nfcActivityScopeAccess.getActivity() ?: return
        nfcBridge.getNfcAdapter()?.disableReaderMode(activity)
    }

    @Deprecated("Recommend uses enable method instead of")
    override fun enableForeGround() {
        val activity = nfcActivityScopeAccess.getActivity() ?: return
        val pendingIntent = PendingIntent.getActivity(
            activity,
            0,
            Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            FLAG_IMMUTABLE
        )
        val intentFilters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        )

        val techList = arrayOf(
            arrayOf(NfcA::class.java.name),
            arrayOf(NfcB::class.java.name),
            arrayOf(NfcF::class.java.name),
            arrayOf(NfcV::class.java.name),
            arrayOf(NfcBarcode::class.java.name),
        )

        nfcBridge.getNfcAdapter()
            ?.enableForegroundDispatch(activity, pendingIntent, intentFilters, techList)
    }
}