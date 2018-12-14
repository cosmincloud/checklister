package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.repo.ItemRepo
import cloud.cosmin.checklister.service.ConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

import java.util.Optional
import java.util.UUID

@Controller
class ItemController {
    @Autowired
    private val itemRepo: ItemRepo? = null

    @Autowired
    private val converterService: ConverterService? = null

    @GetMapping("/api/v1/item/{itemId}")
    fun getListItem(@PathVariable itemId: UUID?): ResponseEntity<ItemGetDto> {
        if (itemId == null) {
            return ResponseEntity.badRequest().build()
        }

        val optionalItem = itemRepo!!.findById(itemId)

        if (!optionalItem.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val dto = converterService!!.itemDto(optionalItem.get())
        return ResponseEntity.ok(dto)
    }

    @PutMapping("/api/v1/item/{itemId}")
    fun updateItem(@PathVariable itemId: UUID?,
                   @RequestBody itemPost: ItemPostDto): ResponseEntity<ItemGetDto> {
        if (itemId == null) {
            return ResponseEntity.badRequest().build()
        }

        val optionalItem = itemRepo!!.findById(itemId)
        if (!optionalItem.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val item = optionalItem.get()
        item.content = itemPost.content
        item.contentType = itemPost.contentType

        val saved = itemRepo.save(item)
        val dto = converterService!!.itemDto(saved)
        return ResponseEntity.ok(dto)

    }
}
