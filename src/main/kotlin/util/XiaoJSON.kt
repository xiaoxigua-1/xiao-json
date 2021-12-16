package xiaoJson.util

import xiaoJson.error.XiaoJSONSyntaxError
import xiaoJson.util.nodes.*
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
                Tokens.RIGHT_CURLY_BRACKETS.str, Tokens.RIGHT_SQUARE_BRACKETS.str -> mergeNodes()
                Tokens.DOUBLE_QUOTES.str -> {
                    val node = nodes[nodes.size - 1]

                    if (!isValue) {
                        if (node is ObjectNode) {
                            keysPush(string().value)
                            node.value[keysCurrently] = TemplateNode(null)
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
                else -> {
                    if (isValue) {
                         when (stringStream.currently) {
                            't', 'f' -> nodes.add(boolean())
                            in ('0'..'9') + '.' + '-' -> nodes.add(number())
                            else -> XiaoJSONSyntaxError("")
                        }
                    } else throw XiaoJSONSyntaxError("")
                }
            }

            stringStream.next()
        }
    }

    private fun string(): StringNode {
        var str = ""

        stringStream.next()

        while (stringStream.currently != Tokens.DOUBLE_QUOTES.str) {
            if (stringStream.isEOF) throw XiaoJSONSyntaxError("Unexpected end of JSON input")
            str += stringStream.next()
        }

        return StringNode(str)
    }

    private fun boolean(): BooleanNode {
        var str = ""

        while (stringStream.currently !in Tokens.values().map { it.str }) {
            str += stringStream.next()
        }

        stringStream.back()

        return when (str) {
            "true" -> BooleanNode(true)
            "false" -> BooleanNode(false)
            else -> throw XiaoJSONSyntaxError("")
        }
    }

    private fun number(): Node {
        var str = ""
        var isFloat = false

        while (stringStream.currently !in Tokens.values().map { it.str }) {
            str += stringStream.next()
            if (stringStream.currently == '.') isFloat = true
        }

        stringStream.back()
        return if (isFloat) FloatNode(str.toDouble())
        else NumberNode(str.toLong())
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

    val getNode: Node
        get() = nodes[0]
}