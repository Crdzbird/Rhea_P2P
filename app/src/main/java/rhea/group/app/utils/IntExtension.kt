package rhea.group.app.utils

fun Int.toMinutesAndSeconds() =
    String.format("%02d:%02d", this / 60, this % 60)

fun Int.toSeconds() =
    String.format(":%02d", this % 60)

fun Int.toSecond() = this * 60