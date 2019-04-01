package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ListEntity
import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.dto.ListPostDto
import cloud.cosmin.checklister.lib.dto.ListWithItemsDto
import cloud.cosmin.checklister.repo.ListRepo
import cloud.cosmin.checklister.service.event.ListEventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ListService @Autowired constructor(
        private val listRepo: ListRepo,
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
        for (item in list.items!!) {
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
}