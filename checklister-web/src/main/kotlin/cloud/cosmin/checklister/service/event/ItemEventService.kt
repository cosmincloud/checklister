package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.ItemEvents
import cloud.cosmin.checklister.lib.event.model.ItemCreateEvent
import cloud.cosmin.checklister.lib.event.model.ItemRankEvent
import cloud.cosmin.checklister.lib.event.model.ItemUpdateEvent
import cloud.cosmin.checklister.lib.event.model.RankOperation
import cloud.cosmin.checklister.lib.event.sink.EventSink
import cloud.cosmin.checklister.service.UuidService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Converts item actions into events.
 */
@Service
class ItemEventService @Autowired
constructor(private val uuidService: UuidService,
            private val eventSink: EventSink) : ItemEvents {
    override fun create(dto: ItemGetDto) {
        val event = ItemCreateEvent(uuidService.get(), dto)
        eventSink.accept(event)
    }

    override fun update(before: ItemGetDto, after: ItemGetDto) {
        val event = ItemUpdateEvent(uuidService.get(), before, after)
        eventSink.accept(event)
    }

    override fun rank(op: RankOperation, before: ItemGetDto, after: ItemGetDto) {
        val event = ItemRankEvent(uuidService.get(), op, before, after)
        eventSink.accept(event)
    }
}