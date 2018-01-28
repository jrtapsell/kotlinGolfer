package uk.co.jrtapsell.kotlinMin

object Minifier {
    private var NO_WS_SPACE_OPS =
            listOf("=", ">", "<", "!", ":", "\\{", "\\}", "\\(", "\\)", "\\+", ",", "-", "\\*", "/", "%", "&&", "\\|\\|")
                    .map { Regex("(\\s+)?$it(\\s+)?") to it}
    private val NO_LINE_AFTER = listOf("{", "->", "[", "(", ".", ",", "&&", "?:", "?.", "|")
    private val NO_LINE_BEFORE = listOf("}", ".", ",", "&&", "+", "|")

    fun minify(submissionLines: List<String>): String {
        val subMinLines = submissionLines.map {
            var output = it.trim() // Ending and starting spaces cannot have meaning
            NO_WS_SPACE_OPS.forEach { output = output.replace(it.first, it.second) } // Some operators never need spaces
            output = output.replace(Regex("//.*"), "") // Strip out line comments
            output = output.replace(Regex("/\\*.*\\*/"), "") // Strip out block comments
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
                outText += System.lineSeparator()
            }
            outText
        }.joinToString("")
    }
}