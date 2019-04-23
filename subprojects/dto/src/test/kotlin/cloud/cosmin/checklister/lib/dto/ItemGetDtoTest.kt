package cloud.cosmin.checklister.lib.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.util.*

internal class ItemGetDtoTest {
    @Test
    fun testSerialization() {
        val item = ItemGetDto(
                id = UUID.randomUUID(),
                list = UUID.randomUUID(),
                content = "content",
                contentType = "contentType",
                rank = 1,
                createdAt = OffsetDateTime.now(),
                lastModified = OffsetDateTime.now())
        val dto = item.toMap()
        assertEquals(item, ItemGetDto.fromMap(dto))
    }
}