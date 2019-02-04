package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemGetDto
import java.util.*

/**
 * Actions on items that can be audited.
 */
interface ItemEvents {
    fun create(id: UUID, dto: ItemGetDto)
    fun update(id: UUID, dto: ItemGetDto)
    fun rank(id: UUID, op: RankOperation, dto: ItemGetDto)
}