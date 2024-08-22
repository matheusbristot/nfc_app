package com.bristot.lockthings

import android.app.Application
import com.bristot.light.di.loadDataDI
import com.bristot.light.nfc.NfcActivityCallbackFactory
import com.bristot.lockthings.lock_unlock.loadLockUnlockDI
import com.bristot.lockthings.register.loadRegisterDI
import com.bristot.lockthings.util.loadUtilDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class LockThingsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(
            NfcActivityCallbackFactory.getInstance().getLifecycleCallback()
        )
        startKoin {
            androidContext(this@LockThingsApplication)
            androidLogger(Level.DEBUG)
            loadDataDI()
            loadRegisterDI()
            loadUtilDI()
            loadLockUnlockDI()
        }
    }
}