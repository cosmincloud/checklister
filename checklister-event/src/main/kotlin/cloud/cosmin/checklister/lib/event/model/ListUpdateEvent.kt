package cloud.cosmin.checklister.lib.event.model

import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.event.Event

data class ListUpdateEvent(val before: ListGetDto, val after: ListGetDto) : Event