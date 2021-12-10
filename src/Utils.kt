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
