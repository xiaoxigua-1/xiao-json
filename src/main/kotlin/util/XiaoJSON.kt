package xiaoJson.util

import xiaoJson.util.nodes.Node
import xiaoJson.util.nodes.StringNode
import xiaoJson.util.tokens.Tokens

class Parser(private val jsonString: String) {
    private val stringStream = StringStream(jsonString)
    private val jsonObject: MutableMap<String, Node> = mutableMapOf()

    fun jsonParser() {
        val nodes = mutableListOf<Node>()

        while (!stringStream.isEOF) {
            stringStream.next()

        }
    }

    fun string(): StringNode {
        var str = ""

        stringStream.next()

        while (!stringStream.isEOF && (stringStream.currently != Tokens.DOUBLE_QUOTES.str)) {
            str += stringStream.next()
        }

        return StringNode(str)
    }
}