import java.util.regex.Pattern

fun main() {

    fun isHorizontalLine(numbers: List<Int>): Boolean {
        return numbers[0] == numbers[2]
    }

    fun isVerticalLine(numbers: List<Int>): Boolean {
        return numbers[1] == numbers[3]
    }

    fun isDigonalLine(numbers: List<Int>): Boolean {
        return Math.abs(numbers[0] - numbers[2]) == Math.abs(numbers[1] - numbers[3])
    }

    fun countDublettes(input: List<String>, withDiagonal: Boolean): Int {
        // 0,9 -> 5,9
        val pattern = Pattern.compile("\\D")
        val allNumbers = HashSet<Pair<Int, Int>>()
        val multipleNumbers = HashSet<Pair<Int, Int>>()

        input.forEach {
            val numbers = it.split(pattern).filter(String::isNotEmpty).map(Integer::parseInt)
            if ( isHorizontalLine(numbers)  || isVerticalLine(numbers)) {
                for (x in numbers[0].coerceAtMost(numbers[2])..numbers[0].coerceAtLeast(numbers[2])) {
                    for (y in numbers[1].coerceAtMost(numbers[3])..numbers[1].coerceAtLeast(numbers[3])) {
                        val pair = Pair(x, y)
                        if (allNumbers.contains(pair)) {
                            multipleNumbers.add(pair)
                        } else {
                            allNumbers.add(pair)
                        }
                    }
                }
            } else if(isDigonalLine(numbers) && withDiagonal) {
                val len = Math.abs(numbers[0] - numbers[2])
                val pointA = Pair(numbers[0], numbers[1])
                val pointB = Pair(numbers[2], numbers[3])

                for (i in 0..len) {
                    val pair = when {
                        (pointA.first < pointB.first && pointA.second < pointB.second) -> Pair(pointA.first+i, pointA.second+i)
                        (pointA.first < pointB.first && pointA.second > pointB.second) -> Pair(pointA.first+i, pointA.second-i)
                        (pointA.first > pointB.first && pointA.second < pointB.second) -> Pair(pointA.first-i, pointA.second+i)
                        (pointA.first > pointB.first && pointA.second > pointB.second) -> Pair(pointA.first-i, pointA.second-i)
                        else -> throw Error("Impossible!")
                    }
                    if (allNumbers.contains(pair)) {
                        multipleNumbers.add(pair)
                    } else {
                        allNumbers.add(pair)
                    }
                }
            }
        }
        return multipleNumbers.size
    }

    fun part1(input: List<String>): Int {
        return countDublettes(input, false)
    }

    fun part2(input: List<String>): Int {
        return countDublettes(input, true)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
