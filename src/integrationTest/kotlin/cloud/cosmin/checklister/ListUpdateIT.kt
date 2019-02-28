package cloud.cosmin.checklister

import cloud.cosmin.checklister.lib.dto.ItemPostDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ListUpdateIT : BaseIT() {
    @Test
    fun listUpdate() {
        val (id) = createList("testlist")
        val (_, title) = updateList(id!!, "newtitle")
        assertEquals("newtitle", title)
    }

    @Test
    fun moveListItem() {
        val (id) = createList("testlist")
        val itemPost = ItemPostDto(id, "content", "text/plain")
        val (itemId) = addItem(itemPost)

        val (id2) = createList("list2")
        val updatedItem = moveItem(id2!!, itemId!!)
        assertEquals(id2, updatedItem!!.list)
    }
}
