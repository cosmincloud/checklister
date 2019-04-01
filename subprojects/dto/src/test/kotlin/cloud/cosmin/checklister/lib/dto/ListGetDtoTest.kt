package cloud.cosmin.checklister.lib.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ListGetDtoTest {
    @Test
    fun testSerialization() {
        val id = UUID.randomUUID()
        val title = "title"
        val dto = ListGetDto(id, title)
        val map = dto.toMap()
        assertEquals(dto, ListGetDto.fromMap(map))
    }
}