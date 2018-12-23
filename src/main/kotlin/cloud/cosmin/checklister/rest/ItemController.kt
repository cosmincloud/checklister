package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.repo.ItemRepo
import cloud.cosmin.checklister.service.ConverterService
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@Api(description = "Operations on list items", tags = arrayOf("item"))
class ItemController
@Autowired
constructor(
        private val itemRepo: ItemRepo,
        private val converterService: ConverterService
) {
    @GetMapping("/api/v1/item/{itemId}")
    @ApiOperation("Retrieve an item")
    fun getListItem(@PathVariable itemId: UUID?): ResponseEntity<ItemGetDto> {
        if (itemId == null) {
            return ResponseEntity.badRequest().build()
        }

        val optionalItem = itemRepo.findById(itemId)

        if (!optionalItem.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val dto = converterService.itemDto(optionalItem.get())
        return ResponseEntity.ok(dto)
    }

    @PutMapping("/api/v1/item/{itemId}")
    @ApiOperation("Update an item")
    fun updateItem(@PathVariable itemId: UUID,
                   @RequestBody itemPost: ItemPostDto): ResponseEntity<ItemGetDto> {
        val optionalItem = itemRepo.findById(itemId)
        if (!optionalItem.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val item = optionalItem.get()
        item.content = itemPost.content
        item.contentType = itemPost.contentType

        val saved = itemRepo.save(item)
        val dto = converterService.itemDto(saved)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/api/v1/item/{itemId}/rank/up")
    @ApiOperation("Move the item one rank up in its list")
    fun rankUp(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        val entity = itemRepo.rankUp(itemId)
        val dto = converterService.itemDto(entity)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/api/v1/item/{itemId}/rank/down")
    @ApiOperation("Move the item one rank down in its list")
    fun rankDown(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        val entity = itemRepo.rankDown(itemId)
        val dto = converterService.itemDto(entity)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/api/v1/item/{itemId}/rank/top")
    @ApiOperation("Move the item to the top of its list")
    fun rankTop(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        val entity = itemRepo.rankTop(itemId)
        val dto = converterService.itemDto(entity)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/api/v1/item/{itemId}/rank/bottom")
    @ApiOperation("Move the item to the bottom of its list")
    fun rankBottom(@PathVariable itemId: UUID): ResponseEntity<ItemGetDto> {
        val entity = itemRepo.rankBottom(itemId)
        val dto = converterService.itemDto(entity)
        return ResponseEntity.ok(dto)
    }
}
