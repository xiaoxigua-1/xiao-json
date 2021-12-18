package xiaoJson.util.nodes

import xiaoJson.error.XiaoJSONSyntaxError
import xiaoJson.type.XiaoArray

abstract class Node {
    abstract val value: Any?

    inline fun <reified T> get(): T {
        return value as T
    }

    inline fun <reified T> get(key: String): T {
        return if (this is ObjectNode) {
            this.value[key]?.value as T
        } else throw throw TypeCastException()
    }

    val array: XiaoArray
        get() = value as XiaoArray
}
