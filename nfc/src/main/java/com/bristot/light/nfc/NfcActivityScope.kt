package com.bristot.light.nfc

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.bristot.light.nfc.scope.NfcActivityScope
import com.bristot.light.nfc.scope.NfcActivityScopeRegister


interface NfcActivityLifecycleCallback {
    fun getLifecycleCallback(): Application.ActivityLifecycleCallbacks
}

object NfcActivityCallbackFactory {
    fun getInstance(): NfcActivityLifecycleCallback {
        return NfcActivityListener(NfcActivityScope)
    }
}

internal class NfcActivityListener(
    private val register: NfcActivityScopeRegister
) : Application.ActivityLifecycleCallbacks,
    NfcActivityLifecycleCallback {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) = Unit

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) {
        register.setActivity(activity)
    }

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) = Unit

    override fun getLifecycleCallback(): Application.ActivityLifecycleCallbacks {
        return this
    }
}
