package uk.co.jrtapsell.kotlinMin

import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.util.*
import java.util.zip.Deflater

object TIO {
    val sep = '\u00ff'

    private fun encode(input:String): String {
        val temp = deflate(input)
        return Base64.getEncoder().withoutPadding().encodeToString(temp)
    }

    private fun deflate(input: String): ByteArray {
        val deflater = Deflater(9)
        deflater.setInput(toByteArray(input))
        deflater.finish()
        val buf = ByteArray(1000)
        val baos = ByteArrayOutputStream()
        while (!deflater.finished()) {
            val t = deflater.deflate(buf)
            baos.write(buf, 0, t)
        }
        val size = deflater.totalOut
        val temp = ByteArray(size - 6)
        // https://stackoverflow.com/a/5698317/8041461
        System.arraycopy(baos.toByteArray(), 2, temp, 0, size - 6)
        return temp
    }

    private fun toByteArray(input: String) =
            input.map { it.toByte() }.toByteArray()

    /** Creates a link to a submission with the given contents. */
    fun makeURL(header: String, code: String, footer: String): String {
        return "https://tio.run/##" + encode("kotlin$sep$header$sep$code$sep$footer$sep")
    }
}