package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemUpdateDto
import org.springframework.stereotype.Service
import java.util.*

/**
 * Converts item actions into events.
 */
@Service
class ItemEventService(val eventSink: EventSink) : ItemEvents {
    override fun update(dto: ItemUpdateDto) {
        val event = Event()
        eventSink.accept(event)
    }

    override fun rank(id: UUID, op: RankOperation) {
        TODO("not implemented")
    }
}