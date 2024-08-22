package com.bristot.light.di

import com.bristot.light.nfc.adapter.NfcBridgeAdapter
import com.bristot.light.nfc.adapter.NfcBridgeAdapterImpl
import com.bristot.light.nfc.bridge.NfcBridge
import com.bristot.light.nfc.bridge.NfcBridgeImpl
import com.bristot.light.nfc.scope.NfcActivityScope
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

fun loadDataDI() = loadKoinModules(module { factoryNFC() })

private fun Module.factoryNFC() {
    factory<NfcBridge> { (id: String) ->
        NfcBridgeImpl(
            preSetTagId = id,
            context = androidContext()
        )
    }

    factory<NfcBridgeAdapter> { (id: String) ->
        NfcBridgeAdapterImpl(
            nfcBridge = get { parametersOf(id) },
            nfcActivityScopeAccess = NfcActivityScope
        )
    }
}