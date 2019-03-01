package cloud.cosmin.checklister.lib.event.model

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.Event
import java.util.UUID

data class ItemRankEvent(
        override val eventId: UUID,
        val op: RankOperation,
        val before: ItemGetDto,
        val after: ItemGetDto) : Event