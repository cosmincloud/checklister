package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dao.ListEntity
import cloud.cosmin.checklister.dto.*
import cloud.cosmin.checklister.repo.ItemRepo
import cloud.cosmin.checklister.repo.ListRepo
import cloud.cosmin.checklister.service.ConverterService
import cloud.cosmin.checklister.service.UuidService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@Api(description = "Operations on lists", tags = arrayOf("list"))
class ListController @Autowired
constructor(
        private val listRepo: ListRepo,
        private val itemRepo: ItemRepo,
        private val converterService: ConverterService,
        private val uuidService: UuidService
) {
    @GetMapping("/api/v1/list")
    @ApiOperation("Retrieve all lists")
    fun allLists(): ResponseEntity<List<ListGetDto>> {
        val lists = ArrayList<ListGetDto>()
        for (list in listRepo.findAll()) {
            val dto = converterService.listDto(list)
            lists.add(dto)
        }
        return ResponseEntity.ok(lists)
    }

    @PostMapping(value = arrayOf("/api/v1/list"), consumes = arrayOf("application/json"))
    @ApiOperation("Create a new list")
    fun createList(@RequestBody listDto: ListPostDto): ResponseEntity<ListGetDto> {
        val newList = ListEntity()
        newList.id = uuidService.get()
        newList.title = listDto.title
        val saved = listRepo.save(newList)
        val dto = converterService.listDto(saved)
        return ResponseEntity
                .created(URI.create("/api/v1/list/" + saved.id))
                .body(dto)
    }

    @GetMapping("/api/v1/list/{listId}")
    @ApiOperation("Retrieve a single list")
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
    @ApiOperation("Retrieve all items in a list")
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

    // TODO: Add endpoint for direct item access (/api/v1/item/{itemId}) ?
    @GetMapping("/api/v1/list/{listId}/item/{itemId}")
    @ApiOperation("Retrieve a single item from a list")
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

    @PostMapping("/api/v1/list/{listId}/item/{itemId}")
    fun moveListItem(@PathVariable listId: UUID,
                    @PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        val optionalList = listRepo.findById(listId)
        if (!optionalList.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val optionalItem = itemRepo.findById(itemId)
        if (!optionalItem.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val item = optionalItem.get()
        item.list = optionalList.get()

        val savedItem = itemRepo.save(item)
        val savedDto = converterService.itemDto(savedItem)
        return ResponseEntity.ok(savedDto)
    }
}
