fun main() {
    fun part1(input: List<String>): Int {
        val rowLen = input[0].length
        val numbers = input.fold(ArrayList<Int>(rowLen*10)) { acc, value -> acc.addAll(value.split("").filter(String::isNotEmpty).map(Integer::parseInt)); acc }
        val heightMap = HeightMap(numbers.toIntArray(), rowLen)

        return heightMap.numbers.foldIndexed(0) { index, acc, value -> acc+heightMap.getRiskLevel(index)}
    }

    fun part2(input: List<String>): Int {
        val rowLen = input[0].length
        val numbers = input.fold(ArrayList<Int>(rowLen*10)) { acc, value -> acc.addAll(value.split("").filter(String::isNotEmpty).map(Integer::parseInt)); acc }
        val heightMap = HeightMap(numbers.toIntArray(), rowLen)

        return heightMap.numbers.indices
                .mapNotNull { index -> if (heightMap.isLowPoint(index)) heightMap.getBasinIndices(index).size else null }
                .sortedDescending()
                .take(3)
                .reduce{ acc, value -> acc*value}
    }

    fun test() {
        /*
        1 2 3
        4 5 6
        7 8 9
         */
        val heightMap = HeightMap(intArrayOf(1,2,3,4,5,6,7,8,9), 3)

        for(i in heightMap.numbers.indices) {
            val result = when(i) {
                0 -> heightMap.isFirstLine(i) && heightMap.isFirstColumn(i) && !heightMap.isLastColumn(i) && !heightMap.isLastLine(i) && heightMap.getRight(i) == 2 && heightMap.getBottom(i) == 4
                1 -> heightMap.isFirstLine(i) && !heightMap.isFirstColumn(i) && !heightMap.isLastColumn(i) && !heightMap.isLastLine(i) && heightMap.getRight(i) == 3 && heightMap.getBottom(i) == 5 && heightMap.getLeft(i) == 1
                2 -> heightMap.isFirstLine(i) && !heightMap.isFirstColumn(i) && heightMap.isLastColumn(i) && !heightMap.isLastLine(i) && heightMap.getLeft(i) == 2 && heightMap.getBottom(i) == 6
                3 -> !heightMap.isFirstLine(i) && heightMap.isFirstColumn(i) && !heightMap.isLastColumn(i) && !heightMap.isLastLine(i) && heightMap.getTop(i) == 1 && heightMap.getRight(i) == 5 && heightMap.getBottom(i) == 7
                4 -> !heightMap.isFirstLine(i) && !heightMap.isFirstColumn(i) && !heightMap.isLastColumn(i) && !heightMap.isLastLine(i) && heightMap.getTop(i) == 2 && heightMap.getRight(i) == 6 && heightMap.getBottom(i) == 8 && heightMap.getLeft(i) == 4
                5 -> !heightMap.isFirstLine(i) && !heightMap.isFirstColumn(i) && heightMap.isLastColumn(i) && !heightMap.isLastLine(i) && heightMap.getTop(i) == 3 && heightMap.getBottom(i) == 9 && heightMap.getLeft(i) == 5
                6 -> !heightMap.isFirstLine(i) && heightMap.isFirstColumn(i) && !heightMap.isLastColumn(i) && heightMap.isLastLine(i) && heightMap.getTop(i) == 4 && heightMap.getRight(i) == 8
                7 -> !heightMap.isFirstLine(i) && !heightMap.isFirstColumn(i) && !heightMap.isLastColumn(i) && heightMap.isLastLine(i) && heightMap.getRight(i) == 9 && heightMap.getTop(i) == 5 && heightMap.getLeft(i) == 7
                8 -> !heightMap.isFirstLine(i) && !heightMap.isFirstColumn(i) && heightMap.isLastColumn(i) && heightMap.isLastLine(i) && heightMap.getLeft(i) == 8 && heightMap.getTop(i) == 6
                else -> throw IllegalArgumentException()
            }
            check(result) { "Failed for ${i}" }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    test()
    check(part1(testInput) == 15, {"was ${part1(testInput)}"})
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))

}

class HeightMap(numbers: IntArray,rowLen: Int): NumberMatrice(numbers, rowLen) {

    fun isLowPoint(index: Int): Boolean {
        val numberList = ArrayList<Int>(0)

        if (!isFirstLine(index)) {
            numberList.add(getTop(index))
        }
        if (!isLastColumn(index)) {
            numberList.add(getRight(index))
        }
        if (!isLastLine(index)) {
            numberList.add(getBottom(index))
        }
        if (!isFirstColumn(index)) {
            numberList.add(getLeft(index))
        }

        return (numbers[index] < numberList.apply { sort() }[0])
    }

    fun getRiskLevel(index: Int): Int {
        return if (isLowPoint(index)) numbers[index] + 1 else 0
    }

    fun getBasinIndices(index: Int, registeredIndexes: HashSet<Int> = HashSet(0)): Set<Int> {

        // starting low point
        if (registeredIndexes.size == 0) registeredIndexes.add(index)

        val addIndexIfSuitableForBasin = {
            currentIndex: Int,
            newIndex: Int -> Boolean

            val indexValue = numbers[index]
            val newIndexValue = numbers[newIndex]

            if (newIndexValue < 9 && (newIndexValue > indexValue)) {
                registeredIndexes.add(newIndex)
                true
            } else {
                false
            }
        }

        if (!isFirstLine(index) && !registeredIndexes.contains(getTopIndex(index))) {
            if (addIndexIfSuitableForBasin(index, getTopIndex(index))) {
                registeredIndexes.addAll(getBasinIndices(getTopIndex(index), registeredIndexes))
            }
        }
        if (!isLastColumn(index) && !registeredIndexes.contains(getRightIndex(index))) {
            if (addIndexIfSuitableForBasin(index, getRightIndex(index))) {
                registeredIndexes.addAll(getBasinIndices(getRightIndex(index), registeredIndexes))
            }
        }
        if (!isLastLine(index) && !registeredIndexes.contains(getBottomIndex(index))) {
            if (addIndexIfSuitableForBasin(index, getBottomIndex(index))) {
                registeredIndexes.addAll(getBasinIndices(getBottomIndex(index), registeredIndexes))
            }
        }
        if (!isFirstColumn(index) && !registeredIndexes.contains(getLeftIndex(index))) {
            if (addIndexIfSuitableForBasin(index, getLeftIndex(index))) {
                registeredIndexes.addAll(getBasinIndices(getLeftIndex(index), registeredIndexes))
            }
        }

        return registeredIndexes
    }
}

