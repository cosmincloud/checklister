package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemUpdateDto
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

        val itemUpdateDto = ItemUpdateDto(UUID.randomUUID(), "content", "contentType")
        service.update(itemUpdateDto)

        val event = ItemUpdateEvent(itemUpdateDto)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank up event")
    fun testRankUp() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        service.rank(id, RankOperation.UP)

        val event = ItemRankEvent(id, RankOperation.UP)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank down event")
    fun testRankDown() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        service.rank(id, RankOperation.DOWN)

        val event = ItemRankEvent(id, RankOperation.DOWN)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank top event")
    fun testRankTop() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        service.rank(id, RankOperation.TOP)

        val event = ItemRankEvent(id, RankOperation.TOP)
        verify(eventSink).accept(event)
    }

    @Test
    @DisplayName("should sink a rank bottom event")
    fun testRankBottom() {
        val eventSink = mock(EventSink::class.java)
        val service = ItemEventService(eventSink)

        val id = UUID.randomUUID()
        service.rank(id, RankOperation.BOTTOM)

        val event = ItemRankEvent(id, RankOperation.BOTTOM)
        verify(eventSink).accept(event)
    }
}