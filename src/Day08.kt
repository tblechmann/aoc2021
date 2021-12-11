import java.util.*

fun main() {

    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val fourDigitOutputValue = line.split('|')[1].split(' ').filter(String::isNotEmpty)

            val onesFourSevenEights = fourDigitOutputValue.filter {
                when (it.length) {
                    2 -> true
                    4 -> true
                    3 -> true
                    7 -> true
                    else -> false
                }
            }
            acc + onesFourSevenEights.size
        }
    }

    fun part2(input: List<String>): Int {
        return TODO()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    //check(part2(testInput) == 168)

    val input = readInput("Day08")
    println(part1(input))
    //println(part2(input))

}
