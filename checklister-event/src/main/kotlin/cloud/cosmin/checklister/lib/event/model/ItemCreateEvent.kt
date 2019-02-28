package cloud.cosmin.checklister.lib.event.model

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.event.Event

data class ItemCreateEvent(val item: ItemGetDto) : Event
