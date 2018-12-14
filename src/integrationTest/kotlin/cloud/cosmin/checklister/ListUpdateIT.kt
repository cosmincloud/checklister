package cloud.cosmin.checklister

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
}
