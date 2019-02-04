package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.repo.ItemRepo
import cloud.cosmin.checklister.repo.ListRepo
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class ItemService(val listRepo: ListRepo,
                  val itemRepo: ItemRepo,
                  val converterService: ConverterService,
                  val itemEventService: ItemEventService) {
    fun findById(id: UUID): Optional<ItemGetDto> {
        return itemRepo.findById(id)
                .map { converterService.itemDto(it) }
    }

    fun create(createdDto: ItemPostDto): ItemGetDto {
        if (createdDto.list == null) {
            throw RuntimeException("list field cannot be null")
        }

        val listId = createdDto.list!!
        val optionalList = listRepo.findById(listId)
        if (!optionalList.isPresent) {
            throw RuntimeException("list cannot be found")
        }

        val list = optionalList.get()

        val newItem = ItemEntity()
        newItem.content = createdDto.content
        newItem.contentType = createdDto.contentType
        newItem.rank = (list.items!!.size + 1) * 2
        newItem.list = list

        val savedItem = itemRepo.save(newItem)
        val dto = converterService.itemDto(savedItem)
        itemEventService.create(savedItem.id!!, dto)
        return dto
    }

    fun update(id: UUID, updatedDto: ItemPostDto): ItemGetDto {
        val optionalItem = itemRepo.findById(id)
        if (!optionalItem.isPresent) {
            throw RuntimeException("item not found with id: $id")
        }

        val item: ItemEntity = optionalItem.get()
        item.content = updatedDto.content
        item.contentType = updatedDto.contentType

        val saved = itemRepo.save(item)
        val dto = converterService.itemDto(saved)
        itemEventService.update(id, dto)
        return dto
    }

    fun rank(id: UUID, op: RankOperation): ItemGetDto {
        val entity = when(op) {
            RankOperation.UP     -> itemRepo.rankUp(id)
            RankOperation.DOWN   -> itemRepo.rankDown(id)
            RankOperation.TOP    -> itemRepo.rankTop(id)
            RankOperation.BOTTOM -> itemRepo.rankBottom(id)
        }

        val dto = converterService.itemDto(entity)
        itemEventService.rank(id, op, dto)
        return dto
    }
}