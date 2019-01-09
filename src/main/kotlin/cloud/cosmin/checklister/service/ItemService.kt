package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemUpdateDto
import cloud.cosmin.checklister.repo.ItemRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemService
@Autowired constructor(val itemRepo: ItemRepo,
                       val converterService: ConverterService) {
    fun findById(id: UUID): Optional<ItemGetDto> {
        return itemRepo.findById(id)
                .map({ converterService.itemDto(it) })
    }

    fun update(dto: ItemUpdateDto): Optional<ItemGetDto> {
        val optionalItem = itemRepo.findById(dto.id)
        if (!optionalItem.isPresent) {
            return Optional.empty()
        }

        val item = optionalItem.get()
        item.content = dto.content
        item.contentType = dto.contentType

        val saved = itemRepo.save(item)
        return Optional.of(converterService.itemDto(saved))
    }

    fun rank(id: UUID, op: RankOperation): ItemGetDto {
        val entity = when(op) {
            RankOperation.UP     -> itemRepo.rankUp(id)
            RankOperation.DOWN   -> itemRepo.rankDown(id)
            RankOperation.TOP    -> itemRepo.rankTop(id)
            RankOperation.BOTTOM -> itemRepo.rankBottom(id)
        }

        return converterService.itemDto(entity)
    }
}