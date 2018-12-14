package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dao.ListEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

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

        val itemDto = svc.itemDto(item)
        assertEquals(item.id, itemDto.id)
        assertEquals(list.id, itemDto.list)
        assertEquals("content", itemDto.content)
        assertEquals("text/plain", itemDto.contentType)
        assertEquals(Integer.valueOf(0), itemDto.rank)
    }
}