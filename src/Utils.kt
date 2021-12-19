import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Converts 010101011011 array to a integer
 */
fun binaryIntListToInteger(input: List<Int>): Int {
    var result = 0
    input.reversed().forEachIndexed { index, value ->
        when(value) {
            1 -> result += 1 shl index
            0 -> { /* no-op */ }
            else -> throw IllegalArgumentException()
        }
    }
    return result
}

/**
 * Converts 010101011011 string to a integer
 */
fun binaryStringToInteger(input: String): Int {
    return binaryIntListToInteger(input.toCharArray().map { c -> c - '0' })
}

fun <T>transposeMatrix(input: List<List<T>>): List<List<T>> {
    val newHeight = input[0].size
    val newWidth = input.size

    check(input.all { it.size == newHeight })
    val result = ArrayList<ArrayList<T>>(newHeight)

    for (i in 0 until newHeight) {
        val line = ArrayList<T>(newWidth)
        for (j in 0 until newWidth) {
            line.add(input[j][i])
        }
        result.add(line)
    }

    return result
}

fun gauss(n: Double): Int {
    return ((Math.pow(n,2.0) + n) / 2).toInt()
}

fun Map<Char,Char>.getFirstKeyByValue(key: Char): Char? {
    return entries.find { it.value == key}?.key
}

fun IntArray.toNumber(): Int {
    return map { (it + '0'.code).toChar() }.joinToString("").toInt()
}

fun String.toCharSet(): Set<Char> {
    return toCharArray().toSet()
}

open class NumberMatrice(val numbers: IntArray, val rowLen: Int) {
    fun isFirstLine(index: Int): Boolean {
        return index < rowLen
    }

    fun isLastLine(index: Int): Boolean {
        return (index >= numbers.size-rowLen) && (index < numbers.size)
    }

    fun isFirstColumn(index: Int): Boolean {
        return index == 0 || (index % rowLen) == 0
    }

    fun isLastColumn(index: Int): Boolean {
        return (index == numbers.size-1) || isFirstColumn(index+1)
    }

    fun getLeft(index: Int): Int {
        return numbers[getLeftIndex(index)]
    }

    fun getLeftIndex(index: Int): Int {
        return index-1
    }

    fun getRight(index: Int): Int {
        return numbers[getRightIndex(index)]
    }

    fun getRightIndex(index: Int): Int {
        return index+1
    }

    fun getTop(index: Int): Int {
        return numbers[getTopIndex(index)]
    }

    fun getTopIndex(index: Int): Int {
        return index-rowLen
    }

    fun getBottom(index: Int): Int {
        return numbers[getBottomIndex(index)]
    }

    fun getBottomIndex(index: Int): Int {
        return index+rowLen
    }

    fun getTopLeftIndex(index: Int): Int {
        return getTopIndex(index)-1
    }

    fun getTopLeft(index: Int): Int {
        return numbers[getTopLeftIndex(index)]
    }

    fun getTopRightIndex(index: Int): Int {
        return getTopIndex(index)+1
    }

    fun getTopRight(index: Int): Int {
        return numbers[getTopRightIndex(index)]
    }

    fun getBottomLeftIndex(index: Int): Int {
        return getBottomIndex(index)-1
    }

    fun getBottomLeft(index: Int): Int {
        return numbers[getBottomLeftIndex(index)]
    }

    fun getBottomRightIndex(index: Int): Int {
        return getBottomIndex(index)+1
    }

    fun getBottomRight(index: Int): Int {
        return numbers[getBottomRightIndex(index)]
    }

    fun getAdjacentIndices(index: Int): List<Int> {
        val result = ArrayList<Int>(8)

        if (!isFirstLine(index) && !isFirstColumn(index)) {
            result.add(getTopLeftIndex(index))
        }
        if (!isFirstLine(index)) {
            result.add(getTopIndex(index))
        }
        if (!isFirstLine(index) && !isLastColumn(index)) {
            result.add(getTopRightIndex(index))
        }
        if (!isFirstColumn(index)) {
            result.add(getLeftIndex(index))
        }
        if (!isLastColumn(index)) {
            result.add(getRightIndex(index))
        }
        if (!isLastLine(index) && !isFirstColumn(index)) {
            result.add(getBottomLeftIndex(index))
        }
        if (!isLastLine(index)) {
            result.add(getBottomIndex(index))
        }
        if (!isLastLine(index) && !isLastColumn(index)) {
            result.add(getBottomRightIndex(index))
        }

        return result
    }

    companion object {
        fun fromLines(lines: List<String>): NumberMatrice {
            val rowLen = lines[0].length
            val numbers = lines.fold(ArrayList<Int>(rowLen*10)) { acc, value -> acc.addAll(value.split("").filter(String::isNotEmpty).map(Integer::parseInt)); acc }
            return NumberMatrice(numbers.toIntArray(), rowLen)
        }

        fun fromLines(singleInputs: String): NumberMatrice {
            return fromLines(singleInputs.split("\n"))
        }
    }
}
