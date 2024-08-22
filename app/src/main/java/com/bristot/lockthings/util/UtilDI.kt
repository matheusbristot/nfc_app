package com.bristot.lockthings.util

import com.bristot.lockthings.util.resources.LockThingsStringProvider
import com.bristot.lockthings.util.resources.SpannableText
import com.bristot.lockthings.util.resources.SpannableTextImpl
import com.bristot.lockthings.util.resources.StringProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadUtilDI() = loadKoinModules(module { getResourceModules() })

private fun Module.getResourceModules() {
    factory<SpannableText> { SpannableTextImpl(context = androidContext()) }
    factory<StringProvider> {
        LockThingsStringProvider(
            spannableText = get(),
            context = androidContext()
        )
    }
}