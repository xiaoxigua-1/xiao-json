package xiaoJson.util

import xiaoJson.error.XiaoJSONSyntaxError

class StringStream(private val jsonString: String) {
    private var index = 0

    fun next(): Char {
        return jsonString[index++]
    }

    fun back(): Char {
        return jsonString[index--]
    }

    val currently: Char
        get() {
            if (jsonString.length > index)
                return jsonString[index]
            else throw XiaoJSONSyntaxError("Unexpected end of JSON input")
        }

    val isEOF: Boolean
        get() = (jsonString.length - 1) < index
}