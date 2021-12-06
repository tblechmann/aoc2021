fun main() {
    fun calcCommonValues(input: List<String>, defaultValueForNoCommonValue: Int): List<Int> {
        val result = IntArray(input[0].length)

        input.forEach {
            it.forEachIndexed { index, value ->
                when (value) {
                    '0' -> result[index]--
                    '1' -> result[index]++
                }
            }
        }
        return result.map {
            when {
                (it > 0) -> 1
                (it < 0) -> 0
                else -> defaultValueForNoCommonValue
            }
        }
    }

    fun calculateOxygenGeneratorRating(input: List<String>): Int {
        var resultList: List<String> = ArrayList(input)
        for (i in 0 until input[0].length) {
            val calcCommonValues = calcCommonValues(resultList, 1)
            resultList = resultList.filter { (it[i] - '0') == calcCommonValues[i] }

            if (resultList.size == 1) break
        }

        check(resultList.size == 1)
        return binaryStringToInteger(resultList.get(0))
    }

    fun calculateCO2ScrubberRating(input: List<String>): Int {
        var resultList: List<String> = ArrayList(input)
        for (i in 0 until input[0].length) {
            val leastCommonValue = calcCommonValues(resultList, 1).map {
                when (it) {
                    1 -> 0
                    0 -> 1
                    else -> throw IllegalArgumentException()
                }
            }
            resultList = resultList.filter { (it[i] - '0') == leastCommonValue[i]}

            if (resultList.size == 1) break
        }

        check(resultList.size == 1)
        return binaryStringToInteger(resultList.get(0))
    }

    fun part1(input: List<String>): Int {
        var gammaRate = 0
        var epsilonRate = 0

        val commonValues = calcCommonValues(input, -1)

        commonValues.reversed().forEachIndexed { index, value ->
            when(value) {
                1 -> gammaRate += 1 shl index
                0 -> epsilonRate += 1 shl index
                else -> throw IllegalArgumentException()
            }
        }

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        return calculateOxygenGeneratorRating(input) * calculateCO2ScrubberRating(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
