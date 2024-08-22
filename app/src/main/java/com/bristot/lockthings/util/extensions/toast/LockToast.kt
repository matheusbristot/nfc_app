package com.bristot.lockthings.util.extensions.toast

import android.content.Context
import android.widget.Toast

fun Context.shortToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()