import org.assertj.core.api.Assertions.*

fun main() {
    fun part1(input: List<String>): Int {
        val field = OctopusField.fromLines(input)
        var sumOfFlashes = 0
        for (i in 0 until 100) {
            sumOfFlashes += field.increaseEnergy()
        }
        return sumOfFlashes
    }

    fun part2(input: List<String>): Int {
        val field = OctopusField.fromLines(input)
        var i = 1
        while(field.increaseEnergy() != 100) {
            i++
        }
        return i
    }

    fun checkDay11() {
        val testInp1 =
            """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
            """.trimIndent()
        val field = OctopusField.fromLines(testInp1)
        var sumOfFlashes = 0
        for (i in 0 until 100) {
            sumOfFlashes += field.increaseEnergy()
        }
        val afterStep100 =
            """
            0397666866
            0749766918
            0053976933
            0004297822
            0004229892
            0053222877
            0532222966
            9322228966
            7922286866
            6789998766
            """.trimIndent()
        val resultAfterStep1 = OctopusField.fromLines(afterStep100)
        assertThat(field.numbers).isEqualTo(resultAfterStep1.numbers)
        assertThat(sumOfFlashes).isEqualTo(1656)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    checkDay11()
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))

}

typealias OctopusField = NumberMatrice

fun OctopusField.increaseEnergy(): Int {
    // Step one each field is increased by one
    numbers.forEachIndexed {index, _ -> numbers[index]++ }
    /*
    Then, any octopus with an energy level greater than 9 flashes.
    This increases the energy level of all adjacent octopuses by 1,
    including octopuses that are diagonally adjacent.
    If this causes an octopus to have an energy level greater than 9,
    it also flashes.
    This process continues as long as new octopuses keep having their
    energy level increased beyond 9. (An octopus can only flash at most once per step.)
     */
    val highEnergyIndices = ArrayList<Int>(0)
    var highEnergyIndice = findHighEnergyFieldIndice()
    while (highEnergyIndice != null) {
        val adjacentIndices = getAdjacentIndices(highEnergyIndice)
        increaseEnergy(adjacentIndices)
        highEnergyIndices.add(highEnergyIndice)
        // reset all high energy fields
        highEnergyIndices.forEach { numbers[it] = 0 }
        highEnergyIndice = findHighEnergyFieldIndice()
    }
    return highEnergyIndices.size
}

fun OctopusField.findHighEnergyFieldIndices(): List<Int> {
    return numbers.indices.filter { numbers[it] > 9}
}

fun OctopusField.findHighEnergyFieldIndice(): Int? {
    return numbers.indices.find { numbers[it] > 9}
}

fun OctopusField.increaseEnergy(indices: List<Int>) {
    indices.forEach { numbers[it] = ++numbers[it] }
}


