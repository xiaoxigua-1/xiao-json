package xiaoJson

import xiaoJson.util.Parser


data class S(val a: String)

fun main() {
    val parser = Parser("{\"a\": \"c\", \"c\": \"a\", \"d\": [\"a\", {\"a\": \"c\"}]}")
    parser.jsonParser()
    println(parser.getNode[0])
}