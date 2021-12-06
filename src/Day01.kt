fun main() {
    fun part1(input: List<String>): Int {
        var cmp = Integer.MAX_VALUE
        var counter = 0

        input.forEach {
            val parsedValue = Integer.parseInt(it)
            if (parsedValue > cmp) {
                counter ++
            }
            cmp = parsedValue
        }
        return counter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
}
