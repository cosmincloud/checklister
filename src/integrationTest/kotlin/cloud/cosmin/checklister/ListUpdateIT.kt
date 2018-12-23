package cloud.cosmin.checklister

import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.dto.ListGetDto
import org.junit.Test

import org.junit.Assert.assertEquals

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
        val itemPost = ItemPostDto("content", "text/plain", null)
        val (itemId) = addItem(id!!, itemPost)

        val (id2) = createList("list2")
        val updatedItem = moveItem(id2!!, itemId!!)
        assertEquals(id2, updatedItem!!.list)
    }
}
