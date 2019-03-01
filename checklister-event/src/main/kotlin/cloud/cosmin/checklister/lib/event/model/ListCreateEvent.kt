package cloud.cosmin.checklister.lib.event.model

import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.event.Event
import java.util.UUID

data class ListCreateEvent(override val eventId: UUID,
                           val item: ListGetDto) : Event