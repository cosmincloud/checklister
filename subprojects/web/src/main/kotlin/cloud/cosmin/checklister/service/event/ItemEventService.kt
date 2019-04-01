package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.lib.event.ItemEvents
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
            private val eventSink: EventSink,
            private val eventService: EventService) : ItemEvents {
    override fun create(dto: ItemGetDto) {
        val event = Event.create(uuidService.get(), "ITEM_CREATE", dto)
        val bytes = eventSink.accept(event)
        eventService.save(event, bytes)
    }

    override fun update(before: ItemGetDto, after: ItemGetDto) {
        val event = Event.update(uuidService.get(), "ITEM_UPDATE", before, after)
        val bytes = eventSink.accept(event)
        eventService.save(event, bytes)
    }

    override fun rank(operation: RankOperation, before: ItemGetDto, after: ItemGetDto) {
        val event = Event.update(uuidService.get(), "ITEM_RANK_${operation.name.toUpperCase()}", before, after)
        val bytes = eventSink.accept(event)
        eventService.save(event, bytes)
    }
}