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
    override fun create(dto: ItemGetDto) {
        val event = ItemCreateEvent(dto)
        eventSink.accept(event)
    }

    override fun update(before: ItemGetDto, after: ItemGetDto) {
        val event = ItemUpdateEvent(before, after)
        eventSink.accept(event)
    }

    override fun rank(op: RankOperation, before: ItemGetDto, after: ItemGetDto) {
        val event = ItemRankEvent(op, before, after)
        eventSink.accept(event)
    }
}