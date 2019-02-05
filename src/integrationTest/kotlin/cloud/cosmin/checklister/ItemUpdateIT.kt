package cloud.cosmin.checklister

import cloud.cosmin.checklister.dto.ItemPostDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

class ItemUpdateIT : BaseIT() {
    @Test
    fun itemUpdate() {
        val (listId) = createList("testlist")
        val itemPost = ItemPostDto(listId, "content", "text/plain")
        val itemId: UUID?
        run {
            val itemAdded = addItem(itemPost)
            assertNotNull(itemAdded)
            assertEquals("content", itemAdded.content)
            assertEquals("text/plain", itemAdded.contentType)
            assertEquals(2, itemAdded.rank)
            itemId = itemAdded.id
        }
        run {
            itemPost.content = "content1"
            itemPost.contentType = "application/json"
            val itemAdded = addItem(itemPost)
            assertNotNull(itemAdded)
            assertEquals("content1", itemAdded.content)
            assertEquals("application/json", itemAdded.contentType)
            assertEquals(4, itemAdded.rank)
        }
        run {
            val (_, _, content, contentType, rank) = getItem(itemId!!)
            assertEquals("content", content)
            assertEquals("text/plain", contentType)
            assertEquals(2, rank)
        }
    }
}
