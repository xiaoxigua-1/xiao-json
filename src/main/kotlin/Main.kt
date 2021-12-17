package xiaoJson

import xiaoJson.type.XiaoArray
import xiaoJson.type.XiaoObject

fun main() {
    val data = XiaoJSON.parser("{\"a\": {\"a\":\"c\", \"c\": [1,2,3.1]}, \"c\": \"b\", \"d\": [{\"a\": 10}]}")
    println(data)
    println(data.get<XiaoArray>("d")[0].get<Long>("a"))
}