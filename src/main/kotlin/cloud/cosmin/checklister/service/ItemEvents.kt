package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemGetDto
import java.util.*

/**
 * Actions on items that can be audited.
 */
interface ItemEvents {
    fun create(dto: ItemGetDto)
    fun update(before: ItemGetDto, after: ItemGetDto)
    fun rank(operation: RankOperation, before: ItemGetDto, after: ItemGetDto)
}