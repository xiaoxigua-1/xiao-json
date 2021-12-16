package xiaoJson.util

import xiaoJson.error.XiaoJSONParseError
import xiaoJson.util.nodes.ArrayNode
import xiaoJson.util.nodes.Node
import xiaoJson.util.nodes.ObjectNode
import xiaoJson.util.nodes.StringNode
import xiaoJson.util.tokens.Tokens

class Parser(jsonString: String) {
    private val stringStream = StringStream(jsonString)
    private val nodes = mutableListOf<Node>()
    private val keys = mutableListOf<String>()

    fun jsonParser() {
        var isValue = false

        while (!stringStream.isEOF) {
            when (stringStream.currently) {
                Tokens.LEFT_CURLY_BRACKETS.str -> {
                    nodes.add(ObjectNode(mutableMapOf()))
                }
                Tokens.LEFT_SQUARE_BRACKETS.str -> {
                    nodes.add(ArrayNode(mutableListOf()))
                }
                Tokens.RIGHT_CURLY_BRACKETS.str -> mergeNodes()
                Tokens.RIGHT_SQUARE_BRACKETS.str -> mergeNodes()
                Tokens.DOUBLE_QUOTES.str -> {
                    val node = nodes[nodes.size - 1]

                    if (!isValue) {
                        if (node is ObjectNode) {
                            keysPush(string().value)
                            node.value[keysCurrently] = Node()
                        } else if (node is ArrayNode) {
                            node.value.add(string())
                        }

                        nodes[nodes.size - 1] = node
                    } else {
                        if (node is ObjectNode) {
                            node.value[keysCurrently] = string()
                            keysPop()
                        } else if (node is ArrayNode) {
                            node.value.add(string())
                            nodes[nodes.size - 1] = node
                        }
                    }
                }
                Tokens.COLON.str -> isValue = true
                Tokens.COMMA.str -> isValue = false
            }

            stringStream.next()
        }
    }

    private fun string(): StringNode {
        var str = ""

        stringStream.next()

        while ((stringStream.currently != Tokens.DOUBLE_QUOTES.str)) {
            if (stringStream.isEOF) throw XiaoJSONParseError("Missing \"")
            str += stringStream.next()
        }

        return StringNode(str)
    }

    private fun keysPush(key: String) {
        keys.add(key)
    }

    private fun keysPop() {
        keys.removeAt(keys.size - 1)
    }

    private val keysCurrently: String
        get() = keys[keys.size - 1]

    private fun mergeNodes() {
        if (nodes.size - 1 > 0) {
            val node = nodes[nodes.size - 2]

            if (node is ObjectNode) {
                node.value[keysCurrently] = nodes[nodes.size - 1]
                keysPop()
            } else if (node is ArrayNode) {
                node.value.add(nodes[nodes.size - 1])
            }

            nodes[nodes.size - 2] = node
            nodes.removeAt(nodes.size - 1)
        }
    }

    val getNode: MutableList<Node>
        get() = nodes
}