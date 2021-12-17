package xiaoJson.util.nodes

abstract class Node {
    abstract val value: Any?

    inline fun <reified T> get(key: String): T {
        if (this is ObjectNode) {
            return this.value[key]?.value as T
        } else throw throw TypeCastException()
    }
}
