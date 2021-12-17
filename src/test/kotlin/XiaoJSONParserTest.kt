import xiaoJson.XiaoJSON
import xiaoJson.type.XiaoArray
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class XiaoJSONParserTest {
    @Test
    fun arrayTest() {
        val jsonString = this::class.java.getResource("/array.json").readText()
        val parser = XiaoJSON.parser(jsonString)
        assertEquals((parser.array)[0].get<Long>("a"), 10)
        assertEquals((parser.array)[0].get<Long>("b"), 20)
        assertEquals((parser.array)[2].value, 3.0)
    }

    @Test
    fun objectTest() {
        val jsonString = this::class.java.getResource("/object.json").readText()
        val parser = XiaoJSON.parser(jsonString)
        assertEquals(parser.get<Long>("a"), 10)
        assertEquals(parser.get<Double>("b"), 20.3)
        println(parser.get<XiaoArray>("d")[0].value)
        assertEquals<Long>(parser.get<XiaoArray>("d")[0].value as Long, 1)
    }
}