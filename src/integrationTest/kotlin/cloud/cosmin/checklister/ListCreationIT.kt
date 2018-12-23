package cloud.cosmin.checklister

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemPostDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ListCreationIT : BaseIT() {
    @Test
    fun listCreation() {
        val (_, title) = createList("testlist")
        assertEquals("testlist", title)
    }

    @Test
    fun itemCreation() {
        val (id) = createList("testtitle")
        val itemPostUrl = service.http + "/api/v1/list/" + id!!.toString() + "/item"
        val item = ItemPostDto("testcontent", null, null)

        val newItemUri = template.postForLocation(itemPostUrl, item)
        assertNotNull(newItemUri)

        val newItemUriString = service.http + newItemUri!!.toString()
        val newItem = template.getForObject(newItemUriString, ItemGetDto::class.java)
        assertEquals("testcontent", newItem!!.content)
        assertEquals("text/plain", newItem.contentType)
        assertEquals(2, newItem.rank)
    }
}
