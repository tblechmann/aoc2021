import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line -> acc+SyntaxLine(line).getSyntaxErrorValue() }
    }

    fun part2(input: List<String>): Long {
        return input.map { SyntaxLine(it) }
            .filter { it.errorChar == null }
            .map {  it.getAutoCompleteScore()}
            .sorted()
            .let { it.get(it.size/2) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

class SyntaxLine(line: String) {

    val stack = Stack<Char>()
    var errorChar: Char? = null
    val errorValues = mapOf(
    Pair('(', 3),
    Pair(')', 3),
    Pair('[', 57),
    Pair(']', 57),
    Pair('{', 1197),
    Pair('}', 1197),
    Pair('<', 25137),
    Pair('>', 25137))

    val autoCompletionValues = mapOf(
        Pair('(', 1),
        Pair('[', 2),
        Pair('{', 3),
        Pair('<', 4))

    init {
         for(c in line.toCharArray()) {
            if (c.isOpeningBracket()) {
                stack.push(c)
            } else if (c.matchesOpeningBracket(stack.peek())) {
                stack.pop()
            } else {
                 errorChar = c
                 break
             }
        }
    }

    fun getSyntaxErrorValue(): Int {
        return if (errorChar != null) errorValues[errorChar]!! else 0
    }

    fun getAutoCompleteScore(): Long {
        val score = stack.toArray().reversed().fold(0L) { acc, value -> (acc*5) + autoCompletionValues[value]!! }
        return score
    }
}

fun Char.isOpeningBracket(): Boolean {
    return when(this) {
        '(' -> true
        '[' -> true
        '{' -> true
        '<' -> true
        else -> false
    }
}

fun Char.matchesOpeningBracket(openingBracket: Char): Boolean {
    return when(this) {
        ')' -> openingBracket == '('
        ']' -> openingBracket == '['
        '}' -> openingBracket == '{'
        '>' -> openingBracket == '<'
        else -> false
    }
}
