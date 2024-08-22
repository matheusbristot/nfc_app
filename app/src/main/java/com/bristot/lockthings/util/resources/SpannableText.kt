package com.bristot.lockthings.util.resources

import android.text.SpannableStringBuilder

interface SpannableText {
    fun apply(
        text: String,
        boldPart: String
    ): SpannableStringBuilder
}