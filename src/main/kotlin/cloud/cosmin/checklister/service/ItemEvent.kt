package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemPostDto
import java.util.*

enum class ItemEventType {
    UPDATE, RANK
}

data class ItemUpdateEvent(val type: ItemEventType, val id: UUID, val item: ItemPostDto)

data class ItemRankEvent(val type: ItemEventType, val id: UUID, val op: RankOperation, val newRank: Int)