
fun main() {

    fun optimize(input: List<Int>, part1: Boolean = true): Int {
        val max = input.maxOf { it }
        val min = input.minOf { it }
        var minPosition = Pair(0, Int.MAX_VALUE)

        for (i in min .. max) {
            val fuelSum = input.fold(0) { acc, horizontalPosition ->
                    val costs = if (part1) Math.abs(i - horizontalPosition) else gauss(Math.abs((i - horizontalPosition).toDouble()))
                    //System.out.println("Costs to move from ${horizontalPosition} to ${i} is ${costs}")
                    acc+costs
            }
            // System.out.println("Fuel for Position ${i}: $fuelSum")
            if (fuelSum < minPosition.second) {
                minPosition = Pair(i, fuelSum)
            }
        }

        return minPosition.second
    }

    fun part1(input: List<String>): Int {
        val crabPositions = input[0].split(',').map(Integer::parseInt)

        return optimize(crabPositions)
    }

    fun part2(input: List<String>): Int {
        val crabPositions = input[0].split(',').map(Integer::parseInt)

        return optimize(crabPositions, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))

}
