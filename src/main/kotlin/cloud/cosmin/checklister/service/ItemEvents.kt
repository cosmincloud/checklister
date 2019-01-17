package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemUpdateDto
import java.util.*

/**
 * Actions on items that can be audited.
 */
interface ItemEvents {
    fun update(dto: ItemUpdateDto): Unit
    fun rank(id: UUID, op: RankOperation): Unit
}