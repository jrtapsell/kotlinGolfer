package extensions

/** Extends [List] */
object List_ {
    /**
     * Formats a list of strings as a Markdown code block.
     */
    fun List<String>.stackCode() = this.joinToString(System.lineSeparator()) { "    " + it }
}