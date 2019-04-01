package cloud.cosmin.checklister.lib.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ItemGetDtoTest {
    @Test
    fun testSerialization() {
        val id = UUID.randomUUID()
        val list = UUID.randomUUID()
        val content = "content"
        val contentType = "contentType"
        val rank = 1
        val item = ItemGetDto(id, list, content, contentType, rank)
        val dto = item.toMap()
        assertEquals(item, ItemGetDto.fromMap(dto))
    }
}