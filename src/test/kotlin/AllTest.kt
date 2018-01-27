import org.junit.Test
import uk.co.jrtapsell.kotlinMin.CommandLine
import java.nio.file.Paths

class RunAll {
    @Test
    fun main() {
        val path = Paths.get("/home/james/kotlinGolfer/src/main/kotlin/demo/")

        path.toFile().listFiles().filter { it.isFile }.forEach {
            val fileName = it.toPath().toAbsolutePath().toString()
            CommandLine(fileName).run()
        }
    }
}