package xiaoJson

import xiaoJson.type.XiaoArray
import xiaoJson.type.XiaoObject

fun main() {
    val data = XiaoJSON.parser("{\"a\": {\"a\":\"c\", \"c\": [1,2,3.1]}}")
    println(data)
    println(data.get<XiaoObject>("a"))
}