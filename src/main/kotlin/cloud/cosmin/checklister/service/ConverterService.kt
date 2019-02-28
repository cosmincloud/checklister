package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dao.ListEntity
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
                itemEntity.rank
        )
    }
}
