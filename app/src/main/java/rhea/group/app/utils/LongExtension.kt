package rhea.group.app.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatMillisecondsToMinutesAndSeconds(): String {
    val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    return dateFormat.format(this)
}

fun Long.toSeconds() = this / 1000
fun Long.toMinutes() = this / 1000 / 60
fun Long.toHours() = this / 1000 / 60 / 60
fun Long.toDays() = this / 1000 / 60 / 60 / 24
fun Long.toWeeks() = this / 1000 / 60 / 60 / 24 / 7
