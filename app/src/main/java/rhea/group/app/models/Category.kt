package rhea.group.app.models

import rhea.group.app.R

enum class Category {
    Base,
    Sleep,
    Unknown
}

fun categoryIconResource(category: Category): Int {
    return when (category) {
        Category.Base -> R.drawable.ic_play
        Category.Sleep -> R.drawable.ic_book
        Category.Unknown -> R.drawable.ic_play
    }
}

fun toEnumCategory(param: String): Category {
    return when (param) {
        Category.Base.name.lowercase() -> Category.Base
        Category.Sleep.name.lowercase() -> Category.Sleep
        else -> Category.Unknown
    }
}