package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.entity.ItemEntity
import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.dto.ItemPostDto
import cloud.cosmin.checklister.lib.event.model.RankOperation
import cloud.cosmin.checklister.repository.ItemRepository
import cloud.cosmin.checklister.repository.ListRepository
import cloud.cosmin.checklister.service.event.ItemEventService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service
class ItemService(val listRepo: ListRepository,
                  val itemRepo: ItemRepository,
                  val converterService: ConverterService,
                  val itemEventService: ItemEventService) {
    fun findById(id: UUID): Optional<ItemGetDto> {
        return itemRepo.findById(id)
                .map { converterService.itemDto(it) }
    }

    @Transactional
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
        newItem.rank = (list.items.size + 1) * 2
        newItem.list = list
        newItem.createdAt = OffsetDateTime.now()
        newItem.lastModified = OffsetDateTime.now()

        val insertedItem = itemRepo.save(newItem)
        val dto = converterService.itemDto(insertedItem)
        itemEventService.create(dto)
        return dto
    }

    @Transactional
    fun update(id: UUID, dto: ItemPostDto): ItemGetDto {
        val optionalItem = itemRepo.findById(id)
        if (!optionalItem.isPresent) {
            throw RuntimeException("item not found with id: $id")
        }

        val item: ItemEntity = optionalItem.get()
        val before = converterService.itemDto(item)
        if (dto.list != null && dto.list != item.list!!.id!!) {
            val optionalList = listRepo.findById(dto.list!!)
            if (!optionalList.isPresent) {
                throw RuntimeException("list not found with id: ${dto.list}")
            }
            item.list = optionalList.get()
        }
        item.content = dto.content
        item.contentType = dto.contentType
        item.lastModified = OffsetDateTime.now()

        val saved = itemRepo.save(item)
        val after = converterService.itemDto(saved)
        itemEventService.update(before, after)
        return after
    }

    @Transactional
    fun rank(id: UUID, op: RankOperation): ItemGetDto {
        val optionalItem = itemRepo.findById(id)
        if (!optionalItem.isPresent) {
            throw RuntimeException("item not found with id: $id")
        }

        val item: ItemEntity = optionalItem.get()
        val before = converterService.itemDto(item)

        val modifiedAt = OffsetDateTime.now()
        val updated = when(op) {
            RankOperation.UP     -> itemRepo.rankUp(id, modifiedAt)
            RankOperation.DOWN   -> itemRepo.rankDown(id, modifiedAt)
            RankOperation.TOP    -> itemRepo.rankTop(id, modifiedAt)
            RankOperation.BOTTOM -> itemRepo.rankBottom(id, modifiedAt)
        }

        // this is a hack to avoid another database roundtrip
        updated.lastModified = modifiedAt
        val after = converterService.itemDto(updated)
        itemEventService.rank(op, before, after)
        return after
    }
}