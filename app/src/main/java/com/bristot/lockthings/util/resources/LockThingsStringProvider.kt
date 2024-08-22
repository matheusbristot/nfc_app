package com.bristot.lockthings.util.resources

import android.content.Context
import android.text.Spanned
import androidx.annotation.StringRes
import com.bristot.lockthings.R

class LockThingsStringProvider(
    private val context: Context,
    private val spannableText: SpannableText
) : StringProvider {
    override val registerLockSuccessMessage: String = res(R.string.add_new_lock_success_message)

    override val registerLockErrorMessage: String = res(R.string.add_new_lock_error_message)

    override fun waitTagMessage(tagId: String, action: String): Spanned {
        val text = context.getString(
            R.string.lock_unlock_wait_description_by_action,
            tagId,
            action
        )
        return spannableText.apply(text, tagId)
    }

    override val deviceNotSupportNFCMessage: String = res(R.string.device_not_support_nfc_message)

    override val turnOnNFCMessage: String = res(R.string.turn_on_nfc_message)

    override fun tagNotEqualsMessage(tagId: String): Spanned {
        val text = context.getString(
            R.string.tag_not_equals_message,
            tagId,
        )
        return spannableText.apply(text, tagId)
    }

    override fun tagTimeoutMessage(action: String): Spanned {
        val text = context.getString(
            R.string.tag_timeout_message,
            action,
        )
        return spannableText.apply(text, action)
    }

    override fun tagSuccessMessage(action: String): Spanned {
        val text = context.getString(
            R.string.tag_success_message,
            action,
        )
        return spannableText.apply(text, action)
    }

    private fun res(@StringRes stringId: Int) = context.getString(stringId)
}
