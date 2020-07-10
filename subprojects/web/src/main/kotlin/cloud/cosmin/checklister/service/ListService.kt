package cloud.cosmin.checklister.service

import arrow.core.Either
import cloud.cosmin.checklister.entity.ListEntity
import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.dto.ListPostDto
import cloud.cosmin.checklister.lib.dto.ListWithItemsDto
import cloud.cosmin.checklister.repository.ItemRepository
import cloud.cosmin.checklister.repository.ListRepository
import cloud.cosmin.checklister.service.event.ListEventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ListService @Autowired constructor(
        private val listRepo: ListRepository,
        private val itemRepo: ItemRepository,
        private val converterService: ConverterService,
        private val uuidService: UuidService,
        private val listEventService: ListEventService
) {
    fun findAll(): List<ListGetDto> {
        return listRepo.findAll()
                .map { l -> converterService.listDto(l) }
    }

    fun findById(uuid: UUID): Optional<ListGetDto> {
        return listRepo.findById(uuid)
                .map { l -> converterService.listDto(l) }
    }

    fun findByIdWithItems(uuid: UUID): Optional<ListWithItemsDto> {
        val optionalList = listRepo.findById(uuid)
        if (!optionalList.isPresent) {
            return Optional.empty()
        }

        val list = optionalList.get()
        val dto = ListWithItemsDto(
                list.id,
                list.title,
                ArrayList()
        )
        dto.items = ArrayList()
        for (item in list.items) {
            val itemDto = converterService.itemDto(item)
            dto.items!!.add(itemDto)
        }

        return Optional.of(dto)
    }

    @Transactional
    fun create(listPostDto: ListPostDto): ListGetDto {
        val newList = ListEntity()
        newList.id = uuidService.get()
        newList.title = listPostDto.title
        val saved = listRepo.save(newList)
        val dto = converterService.listDto(saved)
        listEventService.create(dto)
        return dto
    }

    @Transactional
    fun update(id: UUID, listPostDto: ListPostDto): Optional<ListGetDto> {
        val optionalList = listRepo.findById(id)
        if (!optionalList.isPresent) {
            return Optional.empty()
        }

        val list = optionalList.get()
        val beforeDto = converterService.listDto(list)
        list.title = listPostDto.title

        val saved = listRepo.save(list)
        val afterDto = converterService.listDto(saved)
        listEventService.update(beforeDto, afterDto)
        return Optional.of(afterDto)
    }

    /**
     * Merge two lists together, moving the items from the
     * other list into the existing list.
     */
    @Transactional
    fun merge(existingListId: UUID, otherListId: UUID): Either<RuntimeException, UUID> {
        val existingListMaybe = listRepo.findById(existingListId)
        if (existingListMaybe.isEmpty) {
            return Either.Left(RuntimeException("List does not exist, uuid: $existingListId"))
        }

        val existingList = existingListMaybe.get()

        val otherListMaybe = listRepo.findById(otherListId)
        if (otherListMaybe.isEmpty) {
            return Either.Left(RuntimeException("List does not exist, uuid: $otherListId"))
        }

        val otherList = otherListMaybe.get()

        itemRepo.moveItems(otherList.id!!, existingList.id!!)
        return Either.right(existingList.id!!)
    }
}