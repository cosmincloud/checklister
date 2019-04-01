package cloud.cosmin.checklister.lib.eventserde.json

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.Event
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("JsonEventSerializer")
internal class JsonEventSerdeTest {
    private val serde = JsonEventSerde()

    @Test
    @DisplayName("should deserialize an ItemCreateEvent")
    fun test01() {
        val eventId = UUID.randomUUID()
        val itemId = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val dto = ItemGetDto(itemId, listId, "content", "text/plain", 1)
        val event = Event(eventId, "ITEM_CREATE", null, dto.toMap())

        val byteArray = serde.serialize(event)
        assertEquals("{\"id\":\"$eventId\",\"item\":{\"id\":\"$itemId\",\"list\":\"$listId\",\"content\":\"content\",\"contentType\":\"text/plain\",\"rank\":1}}", String(byteArray))
    }

    fun test02() {
        val eventId = UUID.randomUUID()
        val itemId = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val dto = ItemGetDto(itemId, listId, "content", "text/plain", 1)
        val expectedEvent = Event(eventId, "ITEM_CREATE", null, dto.toMap())

        val actualEvent = serde.deserialize("{\"id\":\"$eventId\",\"item\":{\"id\":\"$itemId\",\"list\":\"$listId\",\"content\":\"content\",\"contentType\":\"text/plain\",\"rank\":1}}".toByteArray(), Event::class.java)

        assertEquals(expectedEvent, actualEvent)
    }
}