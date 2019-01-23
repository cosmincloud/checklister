package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.repo.ItemRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemService(val itemRepo: ItemRepo,
                  val converterService: ConverterService,
                  val itemEventService: ItemEventService) {
    fun findById(id: UUID): Optional<ItemGetDto> {
        return itemRepo.findById(id)
                .map({ converterService.itemDto(it) })
    }

    fun update(id: UUID, dto: ItemPostDto): Optional<ItemGetDto> {
        val optionalItem = itemRepo.findById(id)
        if (!optionalItem.isPresent) {
            return Optional.empty()
        }

        val item: ItemEntity = optionalItem.get()
        item.content = dto.content
        item.contentType = dto.contentType

        val saved = itemRepo.save(item)
        itemEventService.update(id, dto)
        return Optional.of(converterService.itemDto(saved))
    }

    fun rank(id: UUID, op: RankOperation): ItemGetDto {
        val entity = when(op) {
            RankOperation.UP     -> itemRepo.rankUp(id)
            RankOperation.DOWN   -> itemRepo.rankDown(id)
            RankOperation.TOP    -> itemRepo.rankTop(id)
            RankOperation.BOTTOM -> itemRepo.rankBottom(id)
        }

        itemEventService.rank(id, op, entity.rank)
        return converterService.itemDto(entity)
    }
}