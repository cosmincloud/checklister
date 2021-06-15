package cloud.cosmin.checklister.controller

import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.dto.ListPostDto
import cloud.cosmin.checklister.lib.dto.ListWithItemsDto
import cloud.cosmin.checklister.service.ListService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@Tag(name = "list", description = "Operations on lists")
class ListController @Autowired
constructor(
        private val listService: ListService
) {
    @GetMapping("/api/v1/list")
    @Operation(summary = "Retrieve all lists")
    fun allLists(): ResponseEntity<List<ListGetDto>> {
        return ResponseEntity.ok(listService.findAll())
    }

    @PostMapping(value = arrayOf("/api/v1/list"), consumes = arrayOf("application/json"))
    @Operation(summary = "Create a new list")
    fun createList(@RequestBody listDto: ListPostDto): ResponseEntity<ListGetDto> {
        val dto = listService.create(listDto)
        return ResponseEntity
                .created(URI.create("/api/v1/list/" + dto.id))
                .body(dto)
    }

    @GetMapping("/api/v1/list/{listId}")
    @Operation(summary = "Retrieve a single list")
    fun getList(@PathVariable listId: UUID): ResponseEntity<ListGetDto> {
        return ResponseEntity.of(listService.findById(listId))
    }

    @PutMapping(value = arrayOf("/api/v1/list/{listId}"))
    fun updateList(@PathVariable listId: UUID,
                   @RequestBody listDto: ListPostDto): ResponseEntity<ListGetDto> {
        return ResponseEntity.of(listService.update(listId, listDto))
    }

    @GetMapping("/api/v1/list/{listId}/item")
    @Operation(summary = "Retrieve all items in a list")
    fun getListWithItems(@PathVariable listId: UUID): ResponseEntity<ListWithItemsDto> {
        return ResponseEntity.of(listService.findByIdWithItems(listId))
    }
}
