package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemUpdateDto
import java.util.*

enum class ItemEventType {
    UPDATE, RANK
}

abstract class ItemEvent(val type: ItemEventType, val id: UUID)

data class ItemUpdateEvent(val payload: ItemUpdateDto) : ItemEvent(ItemEventType.UPDATE, payload.id)

data class ItemRankEvent(val payload: UUID, val op: RankOperation) : ItemEvent(ItemEventType.RANK, payload)