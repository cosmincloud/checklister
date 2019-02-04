package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.service.event.Event
import cloud.cosmin.checklister.service.event.ToStringEventSerializer
import java.util.*

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

data class ItemCreateEvent(
        val type: ItemEventType,
        val id: UUID,
        val item: ItemGetDto) : AbstractEvent()

data class ItemUpdateEvent(
        val type: ItemEventType,
        val id: UUID,
        val item: ItemGetDto) : AbstractEvent()

data class ItemRankEvent(
        val type: ItemEventType,
        val id: UUID,
        val op: RankOperation,
        val item: ItemGetDto) : AbstractEvent()