package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.entity.ItemEntity
import cloud.cosmin.checklister.entity.ListEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.OffsetDateTime
import java.util.UUID

class ConverterServiceTest {
    @Test
    fun testConvert() {
        val svc = ConverterService()

        val entity = ListEntity()
        entity.id = UUID.randomUUID()
        entity.title = "title"

        val (id, title) = svc.listDto(entity)
        assertEquals(entity.id, id)
        assertEquals("title", title)
    }

    @Test
    fun itemConvert() {
        val svc = ConverterService()

        val list = ListEntity()
        list.id = UUID.randomUUID()
        list.title = "title"

        val item = ItemEntity()
        item.id = UUID.randomUUID()
        item.list = list
        item.content = "content"
        item.contentType = "text/plain"
        item.rank = 0
        item.createdAt = OffsetDateTime.now()
        item.lastModified = OffsetDateTime.now()

        val itemDto = svc.itemDto(item)
        assertEquals(item.id, itemDto.id)
        assertEquals(list.id, itemDto.list)
        assertEquals("content", itemDto.content)
        assertEquals("text/plain", itemDto.contentType)
        assertEquals(Integer.valueOf(0), itemDto.rank)
        assertEquals(item.createdAt, itemDto.createdAt)
        assertEquals(item.lastModified, itemDto.lastModified)
    }
}