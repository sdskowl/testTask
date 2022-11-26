import org.mozilla.universalchardet.ReaderFactory
import java.io.File
import java.nio.file.NoSuchFileException


fun main() {
    var countValid = 0
    try {
        val file = File("input.txt")
        ReaderFactory.createBufferedReader(file).use { reader ->
            var line = reader.readLine()
            while (line != null) {
                if (line.isNotBlank()) {
                    val valid = line.trim().validator()
                    if (valid.answer) countValid++
                }
                line = reader.readLine()
            }
            println("Valid passwords: $countValid")
        }
    } catch (e: NoSuchFileException) {
        println("Can't open file 'input.txt'")
    }

}
/**
 * To validate request I use regex and then split request to list
 * looks like [symbol, rangeStart, rangeEnd, password]
 * It wasn't necessary, but I used model. Then you can get any info from model.
 * If start range > end range it will be reversed.
 * Password valid when:
 * 1) Symbol is not out of range
 * */
fun String.validator(): ValidationModel {
    val model = ValidationModel()
    val rgx = Regex(pattern = "^.\\s\\d*-\\d*:\\s\\S*")
    //take from line valid request
    val str = rgx.find(this)?.value
    if (str != null) {
        val (sym, rangeStart, rangeEnd, pwd) = str.split(Regex(pattern = "(\\s|:\\s|-)"))
        val rStart = rangeStart.toIntOrNull()
        val rEnd = rangeEnd.toIntOrNull()
        model.apply {
            if (rStart != null && rEnd != null) {
                symbol = sym
                //reverse range if necessary
                range = if (rStart < rEnd) rStart..rEnd else rEnd..rStart
                password = pwd
                val count = password!!.count { it == symbol!!.first() }
                answer = count in range!!
                description = if (!answer) "Invalid password" else "Password is valid."
            } else {
                description = "Invalid request: ${this@validator}"
            }
        }
    } else {
        model.apply {
            description = "Invalid request: ${this@validator}"
        }
    }
    return model
}