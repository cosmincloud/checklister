package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.entity.EventEntity
import cloud.cosmin.checklister.entity.ItemEntity
import cloud.cosmin.checklister.entity.ListEntity
import cloud.cosmin.checklister.lib.dto.EventDto
import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.dto.ListGetDto
import org.springframework.stereotype.Service

@Service
class ConverterService {
    fun listDto(listEntity: ListEntity): ListGetDto {
        return ListGetDto(
                listEntity.id,
                listEntity.title
        )
    }

    fun itemDto(itemEntity: ItemEntity): ItemGetDto {
        return ItemGetDto(
                itemEntity.id,
                itemEntity.list?.id,
                itemEntity.content,
                itemEntity.contentType,
                itemEntity.rank,
                itemEntity.createdAt!!,
                itemEntity.lastModified!!
        )
    }

    fun eventDto(eventEntity: EventEntity): EventDto {
        return EventDto(
                eventEntity.id!!,
                eventEntity.type!!,
                String(eventEntity.bytes!!)
        )
    }
}
