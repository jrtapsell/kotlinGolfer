package uk.co.jrtapsell.kotlinMin

import extensions.List_.stackCode
import extensions.String_.stackCode
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.logging.Logger

/**
 * Runs the tool.
 *
 * @param name
 *  The path of the file to run on.
 */
class CommandLine (private var name:String){
    private var logger = Logger.getLogger(name)!!

    companion object {
        /** The main method, creates a [CommandLine] and runs it. */
        @JvmStatic
        fun main(args: Array<String>) {
            CommandLine(args[0]).run()
        }
    }
    /** Runs the tool. */
    fun run() {
        logger.info("Processing $name")
        val input = Paths.get(name)
        val outDir = input.resolveSibling("output")
        outDir.makeDirIfNotExists()
        val fileName = input.fileName.toString().split(".")[0]
        val outPost = outDir.resolve(fileName)
        outPost.makeDirIfNotExists()
        val outPostFile = outPost.resolve("out.md")
        val outCodeFile = outPost.resolve("out.kt")
        var allRawLines = input.toFile().readLines()

        if (allRawLines[0].startsWith("package")) {
            allRawLines = allRawLines.subList(1, allRawLines.size)
        }


        val postWriter = PrintWriter(outPostFile.toFile())
        val codeWriter = PrintWriter(outCodeFile.toFile())

        runAnalysis(allRawLines, postWriter, codeWriter, fileName)
        logger.info("Completed $name")
    }

    private fun runAnalysis(allRawLines: List<String>, postWriter: PrintWriter, codeWriter: PrintWriter, fileName: String) {

        val noHistory = allRawLines.filter { !it.startsWith("//HISTORY") }
        val subStart = noHistory.indexOf("//STARTSUB")
        val subEnd = noHistory.indexOf("//ENDSUB")

        if (subStart == -1) {
            logger.severe("//STARTSUB missing")
            return
        }
        if (subEnd == -1) {
            logger.severe("//ENDSUB missing")
            return
        }

        val headerLines = noHistory.subList(0, subStart)
        val submissionLines = noHistory.subList(subStart + 1, subEnd)
        val testLines = noHistory.subList(subEnd + 1, noHistory.size)

        val messages = Verifier.verify(submissionLines)

        if (messages.isNotEmpty()) {
            messages.forEach {
                when (it.level) {
                    WarnLevel.FAIL -> logger.severe("on line ${it.line}\n\t${it.message}")
                    WarnLevel.WARN -> logger.info("on line ${it.line}\n\t${it.message}")
                    WarnLevel.OK -> throw AssertionError("OK warning?")
                }
            }
            if (messages[0].level == WarnLevel.FAIL) {
                return
            }
        }

        val submission = Minifier.minify(submissionLines)

        val historyRegex = Regex("""([0-9]+)\(([^)]+)\)\[([0-9]+)]\{([^}]+)}""")
        val history = allRawLines.firstOrNull {it.startsWith("//HISTORY")}?.substring(10)
        data class History(val count: Int, val username: String, val id: Int, val description: String)
        val historyData = if (history != null) {
            val historyData = history.split(",")
                .map {
                    val (_, countText, username, idText, description) = historyRegex.matchEntire(it)!!.groupValues
                    History(countText.toInt(10), username, idText.toInt(10), description)
                }
            historyData
        } else {
            listOf()
        }

        val subSize = submission.toCharArray().size
        postWriter.use {
            it.print("# [Kotlin](https://kotlinlang.org), ")
            for (i in historyData) {
                it.print("<s>${i.count}</s> ")
            }
            it.println("$subSize bytes")
            it.println()
            it.println("<!-- language: lang-kotlin -->")
            it.println()
            it.println(submission.stackCode())
            it.println("## Beautified")
            it.println()
            it.println("<!-- language: lang-kotlin -->")
            it.println()
            it.println(submissionLines.stackCode())
            it.println("## Test")
            it.println()
            it.println("<!-- language: lang-kotlin -->")
            it.println()
            it.println(headerLines.stackCode())
            it.println(submission.stackCode())
            it.println(testLines.stackCode())
            it.println("## TIO")
            it.println("[TryItOnline](${TIO.makeURL(
                    headerLines.joinToString("\n"),
                    submission,
                    testLines.joinToString("\n")
            )})")
            if (historyData.isNotEmpty()) {
                it.println("## Edits")
                var current = subSize
                for (i in historyData) {
                    it.println("- ${current - i.count} [${i.username}](https://codegolf.stackexchange.com/users/${i.id}) - ${i.description}")
                    current = i.count
                }
            }
            it.flush()
        }

        codeWriter.use {
            it.println("package output.$fileName")
            it.println("//HEADER ===========================TIO==========================")
            it.println(headerLines.joinToString("\n"))
            it.println("//CODE ========================================================")
            it.println(submission)
            it.println("// FOOTER ========================================================")
            it.println(testLines.joinToString("\n"))
            it.flush()
        }
    }

    private fun Path.makeDirIfNotExists() {
        if (!Files.isDirectory(this)) {
            Files.createDirectory(this)
        }
    }
}