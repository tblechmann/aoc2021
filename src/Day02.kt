fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input.forEach { line ->
            val (token, number) = line.split(' ').let {
                Pair(it[0], Integer.parseInt(it[1]))
            }
            
            when(token) {
                "forward" -> horizontal += number
                "down" -> depth += number
                "up" -> depth -= number
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.forEach { line ->
            val (token, number) = line.split(' ').let {
                Pair(it[0], Integer.parseInt(it[1]))
            }

            when(token) {
                "forward" -> {
                    horizontal += number
                    depth += aim * number
                }
                "down" ->  aim += number
                "up" -> aim -= number
            }
        }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
