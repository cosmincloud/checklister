package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemPostDto
import org.springframework.stereotype.Service
import java.util.*

/**
 * Converts item actions into events.
 */
@Service
class ItemEventService(private val eventSink: EventSink) : ItemEvents {
    override fun update(id: UUID, dto: ItemPostDto) {
        val event = ItemUpdateEvent(ItemEventType.UPDATE, id, dto)
        eventSink.accept(event)
    }

    override fun rank(id: UUID, op: RankOperation, newRank: Int) {
        val event = ItemRankEvent(ItemEventType.RANK, id, op, newRank)
        eventSink.accept(event)
    }
}