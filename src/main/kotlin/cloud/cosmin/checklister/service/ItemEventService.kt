package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.service.event.EventSink
import org.springframework.stereotype.Service
import java.util.*

/**
 * Converts item actions into events.
 */
@Service
class ItemEventService(private val eventSink: EventSink) : ItemEvents {
    override fun create(id: UUID, dto: ItemGetDto) {
        val event = ItemCreateEvent(ItemEventType.CREATE, id, dto)
        eventSink.accept(event)
    }

    override fun update(id: UUID, dto: ItemGetDto) {
        val event = ItemUpdateEvent(ItemEventType.UPDATE, id, dto)
        eventSink.accept(event)
    }

    override fun rank(id: UUID, op: RankOperation, dto: ItemGetDto) {
        val event = ItemRankEvent(ItemEventType.RANK, id, op, dto)
        eventSink.accept(event)
    }
}