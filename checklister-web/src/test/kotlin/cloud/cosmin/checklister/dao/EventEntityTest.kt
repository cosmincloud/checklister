package cloud.cosmin.checklister.dao

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

internal class EventEntityTest {
    @Test
    fun equality() {
        val id = UUID.randomUUID()

        val e1 = EventEntity()
        e1.id = id
        e1.type = "EVENT_TYPE"
        e1.bytes = "test".toByteArray()

        val e2 = EventEntity()
        e2.id = id
        e2.type = "EVENT_TYPE"
        e2.bytes = "test".toByteArray()

        assertTrue(e1.equals(e2))
        assertTrue(e2.equals(e1))

        val e3 = EventEntity()
        e3.id = UUID.randomUUID()
        e3.type = "EVENT_TYPE"
        e3.bytes = "test".toByteArray()

        assertFalse(e1.equals(e3))
        assertFalse(e3.equals(e1))
        assertFalse(e2.equals(e3))
        assertFalse(e3.equals(e2))
    }
}