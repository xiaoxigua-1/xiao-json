package xiaoJson

import xiaoJson.util.Parser
import xiaoJson.util.nodes.Node

class XiaoJSON {
    companion object {
        inline fun <reified T> parser(jsonString: String): T {
            val parser = Parser(jsonString)
            parser.jsonParser()
            return parser.getNode.get<T>()
        }

        @Deprecated("懶的寫")
        fun stringify(value: Any) {
//            val keys = value::class.java.declaredFields.map { it.name }
            // key
            // value idk
        }
    }
}