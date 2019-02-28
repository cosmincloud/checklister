package cloud.cosmin.checklister.lib.event.model

import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.event.Event

data class ListCreateEvent(val item: ListGetDto) : Event