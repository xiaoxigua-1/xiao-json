package xiaoJson

import xiaoJson.util.Parser
import xiaoJson.util.nodes.Node

class XiaoJSON {
    companion object {
        fun parser(jsonString: String): Node {
            val parser = Parser(jsonString)
            parser.jsonParser()
            return parser.getNode
        }

        fun <T> stringify(value: T) {

        }
    }
}