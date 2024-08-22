package com.bristot.lockthings.util.extensions.textview

import android.text.Editable
import android.widget.TextView
import com.bristot.lockthings.util.text.TextWatcher

fun TextView.observeChanges(callback: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher() {
        override fun afterTextChanged(s: Editable) {
            callback(s.toString())
        }
    })
}