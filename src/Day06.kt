
fun main() {
    fun populate(input: List<String>, days: Int): Long {
        val inputLanternFishPopulation = input[0].split(',').map(Integer::parseInt)
        val lanternFishPopulation =
            LanternFishPopulation.createLanternFishPopulationForListOfLanternFishStates(inputLanternFishPopulation)

        for (i in 1..days) {
            lanternFishPopulation.nextDay()
            System.out.println("Day ${i}: ${lanternFishPopulation.getSize()}")
        }

        return lanternFishPopulation.getSize()
    }

    fun part1(input: List<String>): Long {
        return populate(input, 80)
    }

    fun part2(input: List<String>): Long {
        return populate(input, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))

}

class LanternFishPopulation(val state: LongArray) {

    /*

    So, suppose you have a lanternfish with an internal timer value of 3:

 After one day, its internal timer would become 2.
After another day, its internal timer would become 1.
After another day, its internal timer would become 0.
After another day, its internal timer would reset to 6, and it would create a new lanternfish with an internal timer of 8.
After another day, the first lanternfish would have an internal timer of 5, and the second lanternfish would have an internal timer of 7.
     */

    fun getSize(): Long {
        return state.reduce { acc, value -> acc+value }
    }

    fun nextDay() {
        val nextDay = LongArray(state.size)

        state.forEachIndexed { index, numberOfLanternFish ->
            when (index) {
                0 -> {
                    nextDay[6] += numberOfLanternFish
                    nextDay[8] += numberOfLanternFish
                }
                else -> nextDay[index-1] += numberOfLanternFish
            }
        }

        nextDay.copyInto(state)
    }

    companion object {

        fun createLanternFishPopulationForListOfLanternFishStates(lanternFishStateList: List<Int>): LanternFishPopulation {
            val initialState = lanternFishStateList.fold(LongArray(9)) { acc, value -> acc[value]++; acc }

            return LanternFishPopulation(initialState)
        }
    }

}
