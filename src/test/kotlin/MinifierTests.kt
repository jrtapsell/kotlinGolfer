import org.junit.Assert
import org.junit.Test
import uk.co.jrtapsell.kotlinMin.Minifier

class MinifierTests {

    private fun String.minify() = Minifier.minify(this.lines())

    private infix fun Any.assertEquals(expected: Any) {
        Assert.assertEquals(expected, this)
    }

    @Test
    fun testWhitespace() {
        " ".minify().length assertEquals 0
    }

    @Test
    fun testAddition() {
        " 1 + 1 ".minify() assertEquals "1+1"
    }

    @Test
    fun unindent() {
        """    a
            b""".trimMargin().minify() assertEquals "a\nb"
    }

    @Test
    fun testMultiline() {
        """
        fun x() {
            1 + 1
        }""".minify() assertEquals "fun x(){1+1}"
    }

    @Test
    fun testKnowns() {

    }
}