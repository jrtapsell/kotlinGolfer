package extensions

/**
 * @author James Tapsell
 */
import extensions.List_.stackCode

object String_ {
    fun String.stackCode() = this.lines().stackCode()
}