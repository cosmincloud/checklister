package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemPostDto
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
        val itemUpdateDto = ItemPostDto("content", "contentType")
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
        service.rank(id, RankOperation.UP, 1)

        val event = ItemRankEvent(ItemEventType.RANK, id, RankOperation.UP, 1)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank down event")
    fun testRankDown() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        service.rank(id, RankOperation.DOWN, 1)

        val event = ItemRankEvent(ItemEventType.RANK, id, RankOperation.DOWN, 1)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank top event")
    fun testRankTop() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        service.rank(id, RankOperation.TOP, 1)

        val event = ItemRankEvent(ItemEventType.RANK, id, RankOperation.TOP, 1)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank bottom event")
    fun testRankBottom() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        service.rank(id, RankOperation.BOTTOM, 1)

        val event = ItemRankEvent(ItemEventType.RANK, id, RankOperation.BOTTOM, 1)
        verify(eventSink).accept(event)
    }
}