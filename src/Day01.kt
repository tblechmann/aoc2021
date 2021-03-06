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

    fun part2(input: List<String>): Int {
        var cmp = Integer.MAX_VALUE
        var counter = 0

        val intList = ArrayList<Int>(input.map(Integer::parseInt))
        val resultList = ArrayList<String>()

        while (intList.size >= 3) {
                    val sum = intList.removeAt(0) + intList.get(0) + intList.get(1)
                    resultList.add(sum.toString())
        }

        return part1(resultList)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))

}
