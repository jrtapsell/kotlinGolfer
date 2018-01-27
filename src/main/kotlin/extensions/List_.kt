package extensions

/**
 * @author James Tapsell
 */
object List_ {
    fun List<String>.stackCode() = this.joinToString(System.lineSeparator()) { "    " + it }
}