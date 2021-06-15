package cloud.cosmin.checklister.controller

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.dto.ItemPostDto
import cloud.cosmin.checklister.lib.event.model.RankOperation
import cloud.cosmin.checklister.service.ItemService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@Controller
@Tag(name = "item", description = "Operations on list items")
class ItemController
@Autowired
constructor(private val itemService: ItemService) {

    @GetMapping("/api/v1/item/{itemId}")
    @Operation(summary = "Retrieve an item")
    fun getListItem(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.of(itemService.findById(itemId))
    }

    @PostMapping("/api/v1/item")
    @Operation(summary = "Create an item")
    fun createItem(@RequestBody dto: ItemPostDto): ResponseEntity<ItemGetDto> {
        val createdItem = itemService.create(dto)
        return ResponseEntity
                .created(URI.create("/api/v1/item/" + createdItem.id))
                .body(createdItem)
    }


    @PutMapping("/api/v1/item/{itemId}")
    @Operation(summary = "Update an item")
    fun updateItem(@PathVariable itemId: UUID,
                   @RequestBody dto: ItemPostDto): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.update(itemId, dto))
    }

    @PostMapping("/api/v1/item/{itemId}/rank/up")
    @Operation(summary = "Move the item one rank up in its list")
    fun rankUp(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.rank(itemId, RankOperation.UP))
    }

    @PostMapping("/api/v1/item/{itemId}/rank/down")
    @Operation(summary = "Move the item one rank down in its list")
    fun rankDown(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.rank(itemId, RankOperation.DOWN))
    }

    @PostMapping("/api/v1/item/{itemId}/rank/top")
    @Operation(summary = "Move the item to the top of its list")
    fun rankTop(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.rank(itemId, RankOperation.TOP))
    }

    @PostMapping("/api/v1/item/{itemId}/rank/bottom")
    @Operation(summary = "Move the item to the bottom of its list")
    fun rankBottom(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        return ResponseEntity.ok(itemService.rank(itemId, RankOperation.BOTTOM))
    }
}
