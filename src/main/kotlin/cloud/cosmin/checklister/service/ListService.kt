package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ListEntity
import cloud.cosmin.checklister.dto.ListGetDto
import cloud.cosmin.checklister.dto.ListPostDto
import cloud.cosmin.checklister.dto.ListWithItemsDto
import cloud.cosmin.checklister.repo.ListRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ListService @Autowired constructor(
        private val listRepo: ListRepo,
        private val converterService: ConverterService,
        private val uuidService: UuidService
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

    fun create(listPostDto: ListPostDto): ListGetDto {
        val newList = ListEntity()
        newList.id = uuidService.get()
        newList.title = listPostDto.title
        val saved = listRepo.save(newList)
        return converterService.listDto(saved)
    }

    fun update(id: UUID, listPostDto: ListPostDto): Optional<ListGetDto> {
        val optionalList = listRepo.findById(id)
        if (!optionalList.isPresent) {
            return Optional.empty()
        }

        val list = optionalList.get()
        list.title = listPostDto.title

        val saved = listRepo.save(list)
        return Optional.of(converterService.listDto(saved))
    }
}