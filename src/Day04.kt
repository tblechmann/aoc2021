import java.util.regex.Pattern

fun main() {

    fun parseBoards(inputBingoBoards: List<String>): ArrayList<List<List<BoardEntry>>> {
        val boardLen = inputBingoBoards.indexOf("")
        val numberOfBoards = (inputBingoBoards.size + 1) / (boardLen + 1)

        val boards = ArrayList<List<List<BoardEntry>>>()
        for (i in 0 until numberOfBoards) {
            val board = ArrayList<List<BoardEntry>>()
            for (j in 0 until boardLen) {
                val boardLineNumbers = inputBingoBoards[i*(boardLen+1)+j]
                    .split(Pattern.compile("\\s+"))
                    .filterNot { it.isEmpty() }
                    .map(Integer::parseInt)
                    .toIntArray()
                    .map { BoardEntry(it,false) }
                board.add(boardLineNumbers)
            }
            boards.add(board)
        }

        return boards
    }

    fun markNumber(board: List<List<BoardEntry>>, number: Int) {
            board.flatten().forEach {
                if (it.number == number) {
                    it.marked = true
                }
            }
    }

    fun boardFinished(boards: List<List<BoardEntry>>): Boolean {

        // horizontal
        boards.forEach {
            val result = it.all { it.marked }
            if (result) {
                return true
            }
        }

        // vertical
        val transposed = transposeMatrix(boards)
        transposed.forEach {
            val result = it.all { it.marked }
            if (result) {
                return true
            }
        }

        // cross
        var upperLeftToLowerRight = true
        var lowerLeftToUpperRight = true
        for (i in boards.indices) {
            upperLeftToLowerRight = upperLeftToLowerRight.and(boards[i][i].marked)
            lowerLeftToUpperRight = lowerLeftToUpperRight.and(boards[boards.size-1-i][boards.size-1-i].marked)
        }

        return upperLeftToLowerRight.or(lowerLeftToUpperRight)
    }

    fun markBoards(numbers: IntArray, bingoBoards: ArrayList<List<List<BoardEntry>>>): Pair<List<List<BoardEntry>>, Int> {
        for (number in numbers) {
            for (board in bingoBoards) {
                markNumber(board, number)
                if (boardFinished(board)) {
                    return Pair(board, number)
                }
            }
        }

        return throw Error("No Winner Found")
    }

    fun calcScore(board: List<List<BoardEntry>>, winningNumber: Int): Int {
        return board.flatten().filterNot { it.marked }.fold(0) { acc, it -> acc + it.number }*winningNumber
    }

    fun part1(input: List<String>): Int {
        val numbers = input[0].split(',').map(Integer::parseInt).toIntArray()
        val inputBingoBoards = input.subList(2, input.size)
        val bingoBoards = parseBoards(inputBingoBoards)
        val winner = markBoards(numbers, bingoBoards)

        return calcScore(winner.first, winner.second)
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(',').map(Integer::parseInt).toIntArray()
        val inputBingoBoards = input.subList(2, input.size)
        val bingoBoards = parseBoards(inputBingoBoards)

        var lastWinner: Pair<List<List<BoardEntry>>, Int>?
        while (bingoBoards.size > 1) {
            lastWinner = markBoards(numbers, bingoBoards)
            bingoBoards.remove(lastWinner.first)
        }
        lastWinner = markBoards(numbers, bingoBoards)

        return calcScore(lastWinner!!.first, lastWinner!!.second)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

data class BoardEntry(val number: Int, var marked: Boolean)
