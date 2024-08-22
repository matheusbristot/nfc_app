package com.bristot.lockthings.util.resources

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.bristot.lockthings.R

internal class SpannableTextImpl(private val context: Context) : SpannableText {

    override fun apply(
        text: String,
        boldPart: String
    ): SpannableStringBuilder {
        val spannableString = SpannableStringBuilder(text)
        val start =
            (if (text.indexOf(boldPart) < 0) null else text.indexOf(boldPart))
                ?: return spannableString
        val end = start + boldPart.length
        val color = ForegroundColorSpan(ContextCompat.getColor(context, R.color.black))
        spannableString.setSpan(color, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
}