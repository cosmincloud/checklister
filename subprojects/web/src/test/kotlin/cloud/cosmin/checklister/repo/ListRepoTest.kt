package cloud.cosmin.checklister.repo

import cloud.cosmin.checklister.entity.ListEntity
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import java.util.*

class ListRepoTest {
    private val listRepo: ListRepo? = null

    @Test
    @Ignore
    fun testCreateList() {
        val listEntity = ListEntity()
        listEntity.id = UUID.randomUUID()
        listEntity.title = "title"
        val entity = listRepo!!.save(listEntity)

        val maybeList = listRepo.findById(entity.id!!)
        assertTrue(maybeList.isPresent)
        val savedList = maybeList.get()
        assertNotNull(savedList.id)
        assertEquals("title", savedList.title)
    }
}