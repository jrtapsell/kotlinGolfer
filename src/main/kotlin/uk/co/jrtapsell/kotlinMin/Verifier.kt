package uk.co.jrtapsell.kotlinMin

import uk.co.jrtapsell.kotlinMin.WarnLevel.FAIL
import uk.co.jrtapsell.kotlinMin.WarnLevel.WARN

/**
 * @author James Tapsell
 */
object Verifier {

    class MessagedRegex(val level: WarnLevel, val message: String, pattern: String) {
        private val regex = Regex(pattern)
        fun fails(input: String) = regex.find(input) != null
    }

    data class Failure(val level:WarnLevel, val message: String, val line: Int)

    private val CHECKS = listOf(
            MessagedRegex(FAIL, "Has a multichar variable", """va[lr]\s+[^\s=:]{2}"""),
            MessagedRegex(WARN, "Map *may* be more efficient", """for\s*\("""),
            MessagedRegex(WARN, "Try to reverse iteration", """downTo"""),
            MessagedRegex(WARN, "Map would probably work here", """forEach""")
    )

    fun verify(input: List<String>) = input
            .mapIndexed { index, line -> index to line}
            .flatMap { line -> CHECKS.map { it to line } }
            .filter { it.first.fails(it.second.second) }
            .map { Failure(it.first.level, it.first.message, it.second.first) }
            .sortedBy { (it.line * WarnLevel.values().size) + it.level.ordinal }
}