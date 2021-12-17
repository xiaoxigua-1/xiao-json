package xiaoJson

import xiaoJson.type.XiaoArray
import xiaoJson.type.XiaoObject

fun main() {
    data class A(val a: Int, val b: String)

    val data = XiaoJSON.stringify(A(10, "asdasdasd"))
}