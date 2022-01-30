package rhea.group.app.models

import rhea.group.app.R

enum class BreathType {
    INHALE,
    INHALE_HOLD,
    EXHALE,
    EXHALE_HOLD
}

fun breathTypePosition(position: Int): BreathType {
    return when (position) {
        0 -> BreathType.INHALE
        1 -> BreathType.INHALE_HOLD
        2 -> BreathType.EXHALE
        3 -> BreathType.EXHALE_HOLD
        else -> BreathType.INHALE
    }
}

fun convert(breathType: BreathType): Int {
    return when (breathType) {
        BreathType.INHALE -> R.string.inhale
        BreathType.INHALE_HOLD -> R.string.hold
        BreathType.EXHALE -> R.string.exhale
        BreathType.EXHALE_HOLD -> R.string.hold
    }
}