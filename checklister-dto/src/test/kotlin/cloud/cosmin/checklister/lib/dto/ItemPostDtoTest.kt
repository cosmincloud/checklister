package cloud.cosmin.checklister.lib.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ItemPostDtoTest {
    @Test
    fun testSerialization() {
        val list = UUID.randomUUID()
        val content = "content"
        val contentType = "contentType"
        val item = ItemPostDto(list, content, contentType)
        val dto = item.toMap()
        assertEquals(item, ItemPostDto.fromMap(dto))
    }
}