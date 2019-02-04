package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.service.event.EventSink
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
        val itemUpdateDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.update(id, itemUpdateDto)

        val event = ItemUpdateEvent(ItemEventType.UPDATE, id, itemUpdateDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank up event")
    fun testRankUp() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val itemGetDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(id, RankOperation.UP, itemGetDto)

        val event = ItemRankEvent(ItemEventType.RANK, id, RankOperation.UP, itemGetDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank down event")
    fun testRankDown() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val itemGetDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(id, RankOperation.DOWN, itemGetDto)

        val event = ItemRankEvent(ItemEventType.RANK, id, RankOperation.DOWN, itemGetDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank top event")
    fun testRankTop() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val itemGetDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(id, RankOperation.TOP, itemGetDto)

        val event = ItemRankEvent(ItemEventType.RANK, id, RankOperation.TOP, itemGetDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank bottom event")
    fun testRankBottom() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()
        val itemGetDto = ItemGetDto(id, listId, "content", "contentType", 1)
        service.rank(id, RankOperation.BOTTOM, itemGetDto)

        val event = ItemRankEvent(ItemEventType.RANK, id, RankOperation.BOTTOM, itemGetDto)
        verify(eventSink).accept(event)
    }
}