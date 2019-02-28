package cloud.cosmin.checklister.lib.event.model

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.Event

data class ItemRankEvent(
        val op: RankOperation,
        val before: ItemGetDto,
        val after: ItemGetDto) : Event