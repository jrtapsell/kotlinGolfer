package uk.co.jrtapsell.kotlinMin

object Minifier {
    private var NO_WS_SPACE_OPS =
            listOf("=", ">", "<", "!", ":", "\\{", "\\}", "\\(", "\\)", "\\+", ",", "-", "\\*", "/", "%", "&&", "\\|\\|")
                    .map { Regex("(\\s+)?$it(\\s+)?") to it}
    private val NO_LINE_AFTER = listOf("{", "->", "[", "(", ".", ",", "&&", "?:", "?.", "|")
    private val NO_LINE_BEFORE = listOf("}", ".", ",", "&&", "+", "|")

    fun minify(submissionLines: List<String>): String {
        val subMinLines = submissionLines.map {
            var output = it.trim()
            NO_WS_SPACE_OPS.forEach { output = output.replace(it.first, it.second) }
            output = output.replace(Regex("//.*"), "")
            output = output.replace(Regex("/\\*.*\\*/"), "")
            output
        }.filter { it.isNotEmpty() }

        return subMinLines.mapIndexed { index, s ->
            var outText = s
            val canSkipThis = NO_LINE_AFTER.filter { s.endsWith(it) }.any()

            val canSkipOther = if (index + 1 == subMinLines.size)
                    true
                else
                    NO_LINE_BEFORE.filter { subMinLines[index + 1].startsWith(it) }.any()

            if (!canSkipThis && !canSkipOther) {
                outText += "\n"
            }
            outText
        }.joinToString("")
    }
}