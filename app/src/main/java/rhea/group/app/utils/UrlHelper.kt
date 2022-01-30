package rhea.group.app.utils

fun MutableList<String>.removeLastChunk(): String {
    this.removeAt(this.size - 1)
    return this.joinToString("/")
}

fun MutableList<String>.toStringURL(): String {
    return this.joinToString("/")
}

fun String.getLastChunk(): String {
    val split = this.split("/")
    return split[split.size - 1]
}