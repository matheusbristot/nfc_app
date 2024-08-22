package com.bristot.lockthings.register

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadRegisterDI() = loadKoinModules(module { getRegisterLockIdViewModel() })

private fun Module.getRegisterLockIdViewModel() {
    viewModel { RegisterLockIdViewModel(stringProvider = get()) }
}