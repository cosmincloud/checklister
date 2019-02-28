package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ListGetDto
import cloud.cosmin.checklister.lib.event.Event
import cloud.cosmin.checklister.service.RankOperation

enum class ListEventType {
    CREATE, UPDATE, ADD
}

enum class ItemEventType {
    CREATE, UPDATE, RANK
}

data class ItemCreateEvent(val item: ItemGetDto) : Event

data class ItemUpdateEvent(
        val before: ItemGetDto,
        val after: ItemGetDto) : Event

data class ItemRankEvent(
        val op: RankOperation,
        val before: ItemGetDto,
        val after: ItemGetDto) : Event

data class ListCreateEvent(val item: ListGetDto) : Event

data class ListUpdateEvent(val before: ListGetDto, val after: ListGetDto) : Event