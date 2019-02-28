package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.dto.ItemPostDto
import cloud.cosmin.checklister.lib.event.model.RankOperation
import cloud.cosmin.checklister.service.ItemService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@Controller
@Api(description = "Operations on list items", tags = arrayOf("item"))
class ItemController
@Autowired
constructor(private val itemService: ItemService) {

    @GetMapping("/api/v1/item/{itemId}")
    @ApiOperation("Retrieve an item")
    fun getListItem(@PathVariable itemId: UUID?): ResponseEntity<ItemGetDto> {
        if (itemId == null) {
            return ResponseEntity.badRequest().build()
        }

        val optionalItem = itemService.findById(itemId)

        if (!optionalItem.isPresent) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(optionalItem.get())
    }

    @PostMapping("/api/v1/item")
    @ApiOperation("Create an item")
    fun createItem(@RequestBody dto: ItemPostDto): ResponseEntity<ItemGetDto> {
        val createdItem = itemService.create(dto)
        return ResponseEntity
                .created(URI.create("/api/v1/item/" + createdItem.id))
                .body(createdItem)
    }


    @PutMapping("/api/v1/item/{itemId}")
    @ApiOperation("Update an item")
    fun updateItem(@PathVariable itemId: UUID,
                   @RequestBody dto: ItemPostDto): ResponseEntity<ItemGetDto> {
        val updated = itemService.update(itemId, dto)
        return ResponseEntity.ok(updated)
    }

    @PostMapping("/api/v1/item/{itemId}/rank/up")
    @ApiOperation("Move the item one rank up in its list")
    fun rankUp(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.rank(itemId, RankOperation.UP))
    }

    @PostMapping("/api/v1/item/{itemId}/rank/down")
    @ApiOperation("Move the item one rank down in its list")
    fun rankDown(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.rank(itemId, RankOperation.DOWN))
    }

    @PostMapping("/api/v1/item/{itemId}/rank/top")
    @ApiOperation("Move the item to the top of its list")
    fun rankTop(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.rank(itemId, RankOperation.TOP))
    }

    @PostMapping("/api/v1/item/{itemId}/rank/bottom")
    @ApiOperation("Move the item to the bottom of its list")
    fun rankBottom(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.rank(itemId, RankOperation.BOTTOM))
    }
}
