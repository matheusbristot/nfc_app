package com.bristot.light.nfc.scope

import android.annotation.SuppressLint
import android.app.Activity

@SuppressLint("StaticFieldLeak")
internal object NfcActivityScope : NfcActivityScopeAccess, NfcActivityScopeRegister {
    private var activity: Activity? = null

    override fun getActivity(): Activity? {
        return activity
    }

    override fun setActivity(activity: Activity) {
        this.activity = activity
    }

    override fun clear() {
        activity = null
    }
}

interface NfcActivityScopeAccess {
    fun getActivity(): Activity?
}

interface NfcActivityScopeRegister {
    fun setActivity(activity: Activity)
    fun clear()
}
