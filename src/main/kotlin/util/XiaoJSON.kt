package xiaoJson.util

class Parser(private val jsonString: String) {
    private val stringStream = StringStream(jsonString)
    private val jsonObject: MutableMap<String, String> = mutableMapOf()

    fun jsonParser() {
        var str = ""
        var isString = false
        var isObject = false
        var isArray = false
        var isValue = false
        var value = ""
        var key = ""

        while (stringStream.isEOF) {
            val nextChar = stringStream.next()
            str += nextChar

            when(nextChar) {
                '\"' -> {
                    isString = !isString
                    if (!isString) {
                        if (isValue) {
                            value = str
                        } else {
                            key = str
                        }
                    }
                }
                '{' -> isObject = true
                '}' -> isObject = false
                '[' -> isArray = true
                ']' -> isArray = false
                ':' -> {
                    str = ""
                    isValue = true
                }
                ',' -> {
                    str = ""
                    isValue = false
                }
                else -> {
                    str += nextChar
                }
            }
        }
    }
}