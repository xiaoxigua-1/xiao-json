package xiaoJson

import xiaoJson.util.Parser


data class S(val a: String)

fun main() {
    Parser("\"asdasdasd\"").string()
}