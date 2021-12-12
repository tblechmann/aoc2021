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

    fun createMappingFromUniqueCode(uniqueCodes: List<String>): Map<Char, Char> {
        val result = HashMap<Char, Char>()
        val abcdefg = "abcdefg".toCharSet()
        // find 1,4,7,8
        val one = uniqueCodes.find { it.length == 2 }!!.toCharSet()
        val four = uniqueCodes.find { it.length == 4 }!!.toCharSet()
        val seven = uniqueCodes.find { it.length == 3 }!!.toCharSet()
        //val eight = uniqueCodes.find { it.length == 7}!!.toCharSet()

        // find 0,2,3,5,6,9
        val zeroSixNine = uniqueCodes.filter { it.length == 6 }
        val six = zeroSixNine.find { it.toCharSet().intersect(one).size == 1 }!!.toCharSet()
        val twoThreeFive = uniqueCodes.filter { it.length == 5 }
        val three = twoThreeFive.find { it.toCharArray().toSet().containsAll(one) }!!.toCharSet()

        result['a'] = seven.subtract(one).first() // ok
        result['b'] = four.subtract(three).first() // ok
        result['c'] = abcdefg.minus(six).first() // ok
        result['d'] = four.subtract(one).minus(result['b']!!).first() // ok
        result['e'] = abcdefg.minus(three).minus(four).first()
        result['f'] = one.minus(result['c']!!).first() // ok
        result['g'] = three.subtract(one).minus(result['a']!!).minus(result['d']!!).first()

        return result
    }

    fun decodeNumber(decodedFourDigitCode: String, mapping: Map<Char, Char>): Int {

        val encodedValue =
            decodedFourDigitCode.toCharArray().map { mapping.getFirstKeyByValue(it)!! }.toCharArray().let { it.sort(); it }.joinToString("")

        val encodedNumber =
            when (encodedValue) {
                "abcefg" -> 0
                "cf" -> 1
                "acdeg" -> 2
                "acdfg" -> 3
                "bcdf" -> 4
                "abdfg" -> 5
                "abdefg" -> 6
                "acf" -> 7
                "abcdefg" -> 8
                "abcdfg" -> 9
                else -> throw IllegalArgumentException()
            }

        return encodedNumber
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
            val uniqueCodes = line.split('|')[0].split(' ').filter(String::isNotEmpty)
            val fourDigitOutputValue = line.split('|')[1].split(' ').filter(String::isNotEmpty)
            val mapping = createMappingFromUniqueCode(uniqueCodes)

            val numberString = fourDigitOutputValue.map{ decodeNumber(it, mapping) }.toIntArray().toNumber()
            acc + numberString
        }
    }

    fun test() {
        val line = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
        val uniqueCodes = line.split('|')[0].split(' ').filter(String::isNotEmpty)

        val mapping = createMappingFromUniqueCode(uniqueCodes)
        check(decodeNumber("ab", mapping) == 1)
        check(decodeNumber("fbcad", mapping) == 3)
        check(decodeNumber("eafb", mapping) == 4)
        check(decodeNumber("dab", mapping) == 7)
        check(decodeNumber("gcdfa", mapping) == 2)
        check(decodeNumber("cdfbe", mapping) == 5)
        check(decodeNumber("cdfgeb", mapping) == 6)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    test()
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
