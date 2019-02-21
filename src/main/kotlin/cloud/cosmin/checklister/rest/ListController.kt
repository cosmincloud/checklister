package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.dto.ListGetDto
import cloud.cosmin.checklister.dto.ListPostDto
import cloud.cosmin.checklister.dto.ListWithItemsDto
import cloud.cosmin.checklister.repo.ItemRepo
import cloud.cosmin.checklister.repo.ListRepo
import cloud.cosmin.checklister.service.ConverterService
import cloud.cosmin.checklister.service.ListService
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
        private val listService: ListService
) {
    @GetMapping("/api/v1/list")
    @ApiOperation("Retrieve all lists")
    fun allLists(): ResponseEntity<List<ListGetDto>> {
        return ResponseEntity.ok(listService.findAll())
    }

    @PostMapping(value = arrayOf("/api/v1/list"), consumes = arrayOf("application/json"))
    @ApiOperation("Create a new list")
    fun createList(@RequestBody listDto: ListPostDto): ResponseEntity<ListGetDto> {
        val dto = listService.create(listDto)
        return ResponseEntity
                .created(URI.create("/api/v1/list/" + dto.id))
                .body(dto)
    }

    @GetMapping("/api/v1/list/{listId}")
    @ApiOperation("Retrieve a single list")
    fun getList(@PathVariable listId: UUID): ResponseEntity<ListGetDto> {
        val optionalList = listService.findById(listId)
        if (!optionalList.isPresent) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(optionalList.get())
    }

    @PutMapping(value = arrayOf("/api/v1/list/{listId}"))
    fun updateList(@PathVariable listId: UUID,
                   @RequestBody listDto: ListPostDto): ResponseEntity<ListGetDto> {
        val optionalDto = listService.update(listId, listDto)
        if (optionalDto.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok().body(optionalDto.get())
    }

    @GetMapping("/api/v1/list/{listId}/item")
    @ApiOperation("Retrieve all items in a list")
    fun getListWithItems(@PathVariable listId: UUID): ResponseEntity<ListWithItemsDto> {
        val optionalList = listService.findByIdWithItems(listId)
        if (optionalList.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(optionalList.get())
    }
}
