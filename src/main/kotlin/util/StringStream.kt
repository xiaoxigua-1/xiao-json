package xiaoJson.util

class StringStream(private val jsonString: String) {
    private var index = 0

    fun next(): Char {
        return jsonString[index++]
    }

    fun back(): Char {
        return jsonString[index--]
    }

    val currently: Char
        get() = jsonString[index]

    val isEOF: Boolean
        get() = jsonString.length < index
}