package cloud.cosmin.checklister.repo

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dao.ListEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import java.util.*

class ItemRepoTest {
    private val listRepo: ListRepo? = null
    private val itemRepo: ItemRepo? = null

    @Test
    @Ignore
    fun testCreateListAddItem() {
        val listEntity = ListEntity()
        listEntity.id = UUID.randomUUID()
        listEntity.title = "title"
        val list = listRepo!!.save(listEntity)

        val newItem = ItemEntity()
        newItem.content = "test content"
        newItem.list = list
        newItem.rank = 0
        val savedEntity = itemRepo!!.save(newItem)

        val maybeItem = itemRepo.findById(savedEntity.id!!)
        assertTrue(maybeItem.isPresent)

        val item = maybeItem.get()
        assertEquals("test content", item.content)
        assertEquals("text/plain", item.contentType)
        assertEquals(list.id, item.list!!.id)
        assertEquals(0, item.rank.toLong())
        assertEquals("title", list.title)

        val maybeListWithItem = listRepo.findById(list.id!!)
        assertTrue(maybeListWithItem.isPresent)

        val listWithItem = maybeListWithItem.get()
        assertEquals(1, listWithItem.items!!.size.toLong())
    }
}