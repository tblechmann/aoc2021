import java.util.*
import kotlin.collections.HashMap

fun main() {

    fun findOnesFourSevenEights(input: List<String>): List<String> {
        return input.filter {
            when (it.length) {
                2 -> true
                4 -> true
                3 -> true
                7 -> true
                else -> false
            }
        }
    }

    fun createMapping(onesFourSevenEights: List<String>): Map<Char, Char> {
        var result = HashMap<Char, Char>()

        val accAssign =  { encoded: String, correctCode: String, acc: HashMap<Char, Char> ->
            for (i in encoded.indices) {
                acc[encoded[i]] = correctCode[i]
            }
        }

        result = onesFourSevenEights.fold(result) { acc, it ->

            when (it.length) {
                2 -> /* 1 = c, f */ accAssign(it, "cf", acc)
                4 -> /* 4 = b, c, d, f */ accAssign(it, "bcdf", acc)
                3 -> /* 7 = a, c, f */ accAssign(it, "acf", acc)
                7 -> /* 8 = a, b, c, d, e, f, g */ accAssign(it, "abcdefg", acc)
                else -> throw IllegalArgumentException("${it} is not valid with an length of ${it.length}")
            }

            acc
        }

        // any missing value ?
        if (result.size !== "abcdefg".length) {
            TODO()
        }

        return result
    }

    fun decodeNumbers(decodedFourDigitCode: List<String>, mapping: Map<Char, Char>): Int {
        val encodedNumbers = decodedFourDigitCode
            .map { it.map { mapping[it] }.joinToString("") }
            .map {
                when (it) {
                    "abcefg" -> 0
                    "cf" -> 1
                    "aceg" -> 2
                    "acdfg" -> 3
                    "bcdf" -> 4
                    "abdfg" -> 5
                    "abdefg" -> 6
                    "acf" -> 7
                    "abcdefg" -> 8
                    "abcdfg" -> 9
                    else -> throw IllegalArgumentException()
                }
            }

        return Integer.parseInt(encodedNumbers.joinToString(""))
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val fourDigitOutputValue = line.split('|')[1].split(' ').filter(String::isNotEmpty)
            val onesFourSevenEights = findOnesFourSevenEights(fourDigitOutputValue)
            acc + onesFourSevenEights.size
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val fourDigitOutputValue = line.split('|')[1].split(' ').filter(String::isNotEmpty)
            val allCodes = line.split('|', ' ').filter(String::isNotEmpty)
            val onesFourSevenEights = findOnesFourSevenEights(allCodes)
            val mapping = createMapping(onesFourSevenEights)

            acc + decodeNumbers(onesFourSevenEights, mapping)
        }
    }

    fun test() {
        //val line = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
        val line = "acedgfb cdfbe gcdfa fbcad cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
        val allCodes = line.split('|', ' ').filter(String::isNotEmpty)
        val onesFourSevenEights = findOnesFourSevenEights(allCodes)

        val mapping = createMapping(onesFourSevenEights)//listOf("acedgfb", "cdfbe"))
        check(decodeNumbers(listOf("acedgfb"), mapping) === 8)
        check(decodeNumbers(listOf("cdfbe"), mapping) === 5)

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    test()
    // check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    //println(part2(input))

}
