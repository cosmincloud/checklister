package cloud.cosmin.checklister.lib.event.model

import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.event.Event
import java.util.UUID

data class ListUpdateEvent(override val eventId: UUID,
                           val before: ListGetDto, val after: ListGetDto) : Event