package rhea.group.app.utils

import android.text.TextUtils
import android.util.Patterns


class EmailHelper {
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target ?: "").matches()
    }
}