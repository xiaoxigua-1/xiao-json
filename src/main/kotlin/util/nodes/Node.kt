package xiaoJson.util.nodes

abstract class Node {
    abstract val value: Any?

    inline fun <reified T> get(key: String): T {
        if (this is ObjectNode) {
            val value = this.value[key]?.value

            return value as T
        } else throw throw TypeCastException()
    }

    fun getObject(key: String): MutableMap<String, Node> {
        if (this is ObjectNode) {
            return (this.value[key] as ObjectNode).value
        } else throw throw TypeCastException()
    }

    fun getArray(key: String): MutableList<Node> {
        if (this is ObjectNode) {
            return (this.value[key] as ArrayNode).value
        } else throw throw TypeCastException()
    }
}
