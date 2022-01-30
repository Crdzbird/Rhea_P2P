package rhea.group.app.utils

import java.util.*

object UuidParser {
    fun fromString(s: String): UUID {
        Objects.requireNonNull(s)
        require(!(s.length != 36 || s[8] != '-' || s[13] != '-' || s[18] != '-' || s[23] != '-')) { "Invalid UUID-format: $s" }
        val mostSigBits = parseHexStrToLong(s, 0, 18)
        val leastSigBits = parseHexStrToLong(s, 19, 36)
        return UUID(mostSigBits, leastSigBits)
    }

    private fun parseHexStrToLong(s: String, startPos: Int, endPos: Int): Long {
        var result: Long = 0
        for (cursor in startPos until endPos) {
            val digit = hexToDigit(s, cursor)
            if (digit.toInt() == -1) {
                continue
            }
            result = result shl 4
            result -= digit.toLong()
        }
        return -result
    }

    private fun hexToDigit(s: String, position: Int): Byte {
        return when (s[position]) {
            '-' -> -1
            '0' -> 0
            '1' -> 1
            '2' -> 2
            '3' -> 3
            '4' -> 4
            '5' -> 5
            '6' -> 6
            '7' -> 7
            '8' -> 8
            '9' -> 9
            'a', 'A' -> 10
            'b', 'B' -> 11
            'c', 'C' -> 12
            'd', 'D' -> 13
            'e', 'E' -> 14
            'f', 'F' -> 15
            else -> throw IllegalArgumentException(
                String.format(
                    "Invalid UUID-format at position %d: %s",
                    position,
                    s
                )
            )
        }
    }
}