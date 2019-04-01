package cloud.cosmin.checklister.lib.event

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.model.RankOperation

/**
 * Actions on items that can be audited.
 */
interface ItemEvents {
    fun create(dto: ItemGetDto)
    fun update(before: ItemGetDto, after: ItemGetDto)
    fun rank(operation: RankOperation, before: ItemGetDto, after: ItemGetDto)
}