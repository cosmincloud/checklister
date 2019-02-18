package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.service.RankOperation
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*

@DisplayName("ItemEventService")
internal class ItemEventServiceTest {
    @Test
    @DisplayName("should sink an update event")
    fun testUpdateEvent() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.update(beforeDto, afterDto)

        val event = ItemUpdateEvent(beforeDto, afterDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank up event")
    fun testRankUp() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(RankOperation.UP, beforeDto, afterDto)

        val event = ItemRankEvent(RankOperation.UP, beforeDto, afterDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank down event")
    fun testRankDown() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(RankOperation.DOWN, beforeDto, afterDto)

        val event = ItemRankEvent(RankOperation.DOWN, beforeDto, afterDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank top event")
    fun testRankTop() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(RankOperation.TOP, beforeDto, afterDto)

        val event = ItemRankEvent(RankOperation.TOP, beforeDto, afterDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank bottom event")
    fun testRankBottom() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val beforeDto = ItemGetDto(id, listId, "content", "contentType", 1)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(RankOperation.BOTTOM, beforeDto, afterDto)

        val event = ItemRankEvent(RankOperation.BOTTOM, beforeDto, afterDto)
        verify(eventSink).accept(event)
    }
}