@file:Suppress("EnumEntryName")

package rhea.group.app.models

import rhea.group.app.R

enum class FeelingsType {
    positive,
    negative,
    neutral
}

fun feelingIconResource(feelingsType: FeelingsType): Int {
    return when (feelingsType) {
        FeelingsType.positive -> R.drawable.ic_better
        FeelingsType.negative -> R.drawable.ic_worse
        FeelingsType.neutral -> R.drawable.ic_same
    }
}

fun toEnumFeelings(param: String): FeelingsType {
    return when (param) {
        FeelingsType.positive.name.lowercase() -> FeelingsType.positive
        FeelingsType.neutral.name.lowercase() -> FeelingsType.neutral
        FeelingsType.negative.name.lowercase() -> FeelingsType.negative
        else -> FeelingsType.positive
    }
}