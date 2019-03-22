package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.lib.event.model.RankOperation
import cloud.cosmin.checklister.lib.event.sink.EventSink
import cloud.cosmin.checklister.service.UuidService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

@DisplayName("ItemEventService")
internal class ItemEventServiceTest {
    private val eventId = UUID.randomUUID()

    private fun getUuidService(): UuidService {
        val uuidService = mock(UuidService::class.java)
        `when`(uuidService.get()).thenReturn(eventId)
        return uuidService
    }

    @Test
    @DisplayName("should sink an update event")
    fun testUpdateEvent() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(getUuidService(), eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.update(beforeDto, afterDto)

        val event = Event.create(eventId, "ITEM_UPDATE", beforeDto, afterDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank up event")
    fun testRankUp() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(getUuidService(), eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(RankOperation.UP, beforeDto, afterDto)

        val event = Event.create(eventId, "ITEM_RANK_UP", beforeDto, afterDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank down event")
    fun testRankDown() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(getUuidService(), eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(RankOperation.DOWN, beforeDto, afterDto)

        val event = Event.create(eventId, "ITEM_RANK_DOWN", beforeDto, afterDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank top event")
    fun testRankTop() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(getUuidService(), eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(RankOperation.TOP, beforeDto, afterDto)

        val event = Event.create(eventId, "ITEM_RANK_TOP", beforeDto, afterDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank bottom event")
    fun testRankBottom() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(getUuidService(), eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(RankOperation.BOTTOM, beforeDto, afterDto)

        val event = Event.create(eventId, "ITEM_RANK_BOTTOM", beforeDto, afterDto)
        verify(eventSink).accept(event)
    }
}