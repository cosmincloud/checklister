package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.service.RankOperation

/**
 * Actions on items that can be audited.
 */
interface ItemEvents {
    fun create(dto: ItemGetDto)
    fun update(before: ItemGetDto, after: ItemGetDto)
    fun rank(operation: RankOperation, before: ItemGetDto, after: ItemGetDto)
}