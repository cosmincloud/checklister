package cloud.cosmin.checklister.lib.event.model

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.Event
import java.util.UUID

data class ItemCreateEvent(override val eventId: UUID,
                           val item: ItemGetDto) : Event
