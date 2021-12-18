import xiaoJson.XiaoJSON
import xiaoJson.type.XiaoArray
import xiaoJson.type.XiaoObject
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class XiaoJSONParserTest {
    @Test
    fun arrayTest() {
        val jsonString = this::class.java.getResource("/array.json").readText()
        val parser = XiaoJSON.parser<XiaoArray>(jsonString)
        assertEquals(parser[0].get<Long>("a"), 10)
        assertEquals(parser[0].get<Long>("b"), 20)
        assertEquals(parser[2].value, 3.0)
    }

    @Test
    fun objectTest() {
        val jsonString = this::class.java.getResource("/object.json").readText()
        val parser = XiaoJSON.parser<XiaoObject>(jsonString)
        assertEquals(parser["a"]!!.get<Long>(), 10)
        assertEquals(parser["b"]!!.get(), 20.3)
        assertEquals<Long>(parser["d"]!!.get<XiaoArray>()[0].get(), 1)
    }
}