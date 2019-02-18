package cloud.cosmin.checklister.service.event

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.service.RankOperation

enum class ListEventType {
    CREATE, UPDATE, ADD
}

enum class ItemEventType {
    CREATE, UPDATE, RANK
}

abstract class AbstractEvent : Event {
    private val eventSerializer = ToStringEventSerializer()
    override fun serialize(): ByteArray {
        return eventSerializer.serialize(this)
    }
}

data class ListCreateEvent(
        val type: ItemEventType)

data class ItemCreateEvent(val item: ItemGetDto) : AbstractEvent()

data class ItemUpdateEvent(
        val before: ItemGetDto,
        val after: ItemGetDto) : AbstractEvent()

data class ItemRankEvent(
        val op: RankOperation,
        val before: ItemGetDto,
        val after: ItemGetDto) : AbstractEvent()