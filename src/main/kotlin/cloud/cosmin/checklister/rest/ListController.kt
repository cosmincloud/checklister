package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dao.ListEntity
import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.dto.ListGetDto
import cloud.cosmin.checklister.dto.ListPostDto
import cloud.cosmin.checklister.dto.ListWithItemsDto
import cloud.cosmin.checklister.repo.ItemRepo
import cloud.cosmin.checklister.repo.ListRepo
import cloud.cosmin.checklister.service.ConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import java.net.URI
import java.util.ArrayList
import java.util.Optional
import java.util.UUID

@RestController
class ListController @Autowired
constructor(
        private val listRepo: ListRepo,
        private val itemRepo: ItemRepo,
        private val converterService: ConverterService
) {

    val allLists: ResponseEntity<List<ListGetDto>>
        @GetMapping("/api/v1/list")
        get() {
            val lists = ArrayList<ListGetDto>()
            for (list in listRepo.findAll()) {
                val dto = converterService.listDto(list)
                lists.add(dto)
            }
            return ResponseEntity.ok(lists)
        }

    @PostMapping(value = arrayOf("/api/v1/list"), consumes = arrayOf("application/json"))
    fun createList(@RequestBody listDto: ListPostDto): ResponseEntity<ListGetDto> {
        val newList = ListEntity()
        if (listDto.uuid != null) {
            newList.id = listDto.uuid
        } else {
            newList.id = UUID.randomUUID()
        }
        newList.title = listDto.title
        val saved = listRepo.save(newList)
        val dto = converterService.listDto(saved)
        return ResponseEntity
                .created(URI.create("/api/v1/list/" + saved.id))
                .body(dto)
    }

    @GetMapping("/api/v1/list/{listId}")
    fun getList(@PathVariable listId: UUID?): ResponseEntity<ListGetDto> {
        if (listId == null) {
            return ResponseEntity.badRequest().build()
        }

        val optionalList = listRepo.findById(listId)
        if (!optionalList.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val list = optionalList.get()
        return ResponseEntity.ok(converterService.listDto(list))
    }

    @PutMapping(value = arrayOf("/api/v1/list/{listId}"))
    fun updateList(@PathVariable listId: UUID?,
                   @RequestBody listDto: ListPostDto): ResponseEntity<ListGetDto> {
        if (listId == null) {
            return ResponseEntity.badRequest().build()
        }

        val optionalList = listRepo.findById(listId)
        if (!optionalList.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val list = optionalList.get()
        list.title = listDto.title

        val saved = listRepo.save(list)
        val dto = converterService.listDto(saved)
        return ResponseEntity.ok(dto)
    }

    @GetMapping("/api/v1/list/{listId}/item")
    fun getListWithItems(@PathVariable listId: UUID): ResponseEntity<ListWithItemsDto> {
        val optionalList = listRepo.findById(listId)
        if (!optionalList.isPresent) {
            return ResponseEntity.notFound().build()
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
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/api/v1/list/{listId}/item")
    fun createListItem(@PathVariable listId: UUID?,
                       @RequestBody itemDto: ItemPostDto): ResponseEntity<ItemGetDto> {
        if (listId == null) {
            return ResponseEntity.badRequest().build()
        }

        val optionalList = listRepo.findById(listId)
        if (!optionalList.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val list = optionalList.get()

        val newItem = ItemEntity()
        newItem.content = itemDto.content
        newItem.contentType = itemDto.contentType
        newItem.rank = (list.items!!.size + 1) * 2
        newItem.list = list

        val savedItem = itemRepo.save(newItem)
        val dto = converterService.itemDto(savedItem)

        return ResponseEntity
                .created(URI.create("/api/v1/list/" + listId.toString() + "/item/" + savedItem.id))
                .body(dto)
    }

    // TODO: Add endpoint for direct item access (/api/v1/item/{itemId}) ?
    @GetMapping("/api/v1/list/{listId}/item/{itemId}")
    fun getListItem(@PathVariable listId: UUID?,
                    @PathVariable itemId: UUID?): ResponseEntity<ItemGetDto> {
        if (listId == null || itemId == null) {
            return ResponseEntity.badRequest().build()
        }

        val optionalList = listRepo.findById(listId)
        if (!optionalList.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val optionalItem = optionalList.get().items!!.stream()
                .filter { i -> i.id == itemId }
                .findFirst()

        if (!optionalItem.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val dto = converterService.itemDto(optionalItem.get())
        return ResponseEntity.ok(dto)
    }
}
