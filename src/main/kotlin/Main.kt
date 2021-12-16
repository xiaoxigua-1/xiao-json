package xiaoJson

import xiaoJson.util.Parser
import kotlin.reflect.KProperty1

data class S(val a: String)

fun main() {
    val data = XiaoJSON.parser("{\"a\": [1,2,3]}")
    println(data)
    println(data.get<Long>("a"))
}