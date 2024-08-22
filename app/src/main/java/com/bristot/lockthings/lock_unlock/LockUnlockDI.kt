package com.bristot.lockthings.lock_unlock

import com.bristot.lockthings.lock_unlock.bottomsheet.WaitConnectTagBottomViewModel
import com.bristot.lockthings.lock_unlock.fragment.LockUnLockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadLockUnlockDI() = loadKoinModules(module { getLockUnlockViewModels() })

private fun Module.getLockUnlockViewModels() {
    viewModel { (tagId: String, action: String) ->
        WaitConnectTagBottomViewModel(
            tagId = tagId,
            action = action,
            stringProvider = get()
        )
    }

    viewModel { (tagId: String) ->
        LockUnLockViewModel(
            tagId = tagId,
            stringProvider = get()
        )
    }
}
