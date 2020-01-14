package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.lib.event.model.RankOperation
import cloud.cosmin.checklister.lib.event.sink.EventSink
import cloud.cosmin.checklister.service.UuidService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.OffsetDateTime
import java.util.*

@DisplayName("ItemEventService")
internal class ItemEventServiceTest {
    private val eventId = UUID.randomUUID()
    private val date = OffsetDateTime.now()

    private fun getUuidService(): UuidService {
        val uuidService = mock(UuidService::class.java)
        `when`(uuidService.get()).thenReturn(eventId)
        return uuidService
    }

    val eventSink = mock(EventSink::class.java)
    val eventService = mock(EventService::class.java)
    val service = ItemEventService(getUuidService(), eventSink, eventService)

    val byteArray = "".toByteArray()

    @BeforeEach
    fun setUp() {
        reset(eventSink, eventService)
    }

    @Test
    @DisplayName("should sink an update event")
    fun testUpdateEvent() {
        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val event = Event.update(eventId, "ITEM_UPDATE", beforeDto, afterDto)

        `when`(eventSink.accept(event)).thenReturn(byteArray)

        service.update(beforeDto, afterDto)

        verify(eventSink).accept(event)
        verify(eventService).save(event, byteArray)
    }

    @Test
    @DisplayName("should sink a rank up event")
    fun testRankUp() {
        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val event = Event.update(eventId, "ITEM_RANK_UP", beforeDto, afterDto)

        `when`(eventSink.accept(event)).thenReturn(byteArray)

        service.rank(RankOperation.UP, beforeDto, afterDto)

        verify(eventSink).accept(event)
        verify(eventService).save(event, byteArray)
    }

    @Test
    @DisplayName("should sink a rank down event")
    fun testRankDown() {
        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val event = Event.update(eventId, "ITEM_RANK_DOWN", beforeDto, afterDto)

        `when`(eventSink.accept(event)).thenReturn(byteArray)

        service.rank(RankOperation.DOWN, beforeDto, afterDto)

        verify(eventSink).accept(event)
        verify(eventService).save(event, byteArray)
    }

    @Test
    @DisplayName("should sink a rank top event")
    fun testRankTop() {
        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val event = Event.update(eventId, "ITEM_RANK_TOP", beforeDto, afterDto)

        `when`(eventSink.accept(event)).thenReturn(byteArray)

        service.rank(RankOperation.TOP, beforeDto, afterDto)

        verify(eventSink).accept(event)
        verify(eventService).save(event, byteArray)
    }

    @Test
    @DisplayName("should sink a rank bottom event")
    fun testRankBottom() {
        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1, date, date)
        val event = Event.update(eventId, "ITEM_RANK_BOTTOM", beforeDto, afterDto)

        `when`(eventSink.accept(event)).thenReturn(byteArray)

        service.rank(RankOperation.BOTTOM, beforeDto, afterDto)

        verify(eventSink).accept(event)
        verify(eventService).save(event, byteArray)
    }
}