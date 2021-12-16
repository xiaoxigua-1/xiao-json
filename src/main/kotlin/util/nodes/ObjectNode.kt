package xiaoJson.util.nodes

data class ObjectNode(override val value: MutableMap<String, Node>) : Node()
