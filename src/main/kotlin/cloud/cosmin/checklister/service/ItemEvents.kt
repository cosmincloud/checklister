package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemPostDto
import java.util.*

/**
 * Actions on items that can be audited.
 */
interface ItemEvents {
    fun update(id: UUID, dto: ItemPostDto): Unit
    fun rank(id: UUID, op: RankOperation, newRank: Int): Unit
}