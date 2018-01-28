package extensions

import extensions.List_.stackCode

/** Extends String. */
object String_ {
    /** Formats this string as a Markdown code block. */
    fun String.stackCode() = this.lines().stackCode()
}