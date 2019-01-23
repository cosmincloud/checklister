package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.repo.ItemRepo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

@DisplayName("ItemService")
internal class ItemServiceTest {
    @Test @DisplayName("should return an item from the database")
    fun testGet() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()
        val entity = mock(ItemEntity::class.java)
        val dto = ItemGetDto(null, null, null, null, null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemService = ItemService(itemRepo, converterService, eventService)
        val returned = itemService.findById(id).get()
        assertEquals(dto, returned)

        verify(itemRepo).findById(id)
        verify(converterService).itemDto(entity)
    }

    @Test @DisplayName("should return nothing when an entity not found in the DB")
    fun testGetNotFound() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)
        val id = UUID.randomUUID()

        `when`(itemRepo.findById(id)).thenReturn(Optional.empty())

        val itemService = ItemService(itemRepo, converterService, eventService)
        val returned = itemService.findById(id)
        assertTrue(returned.isEmpty)

        verify(itemRepo).findById(id)
        verifyZeroInteractions(converterService)
    }

    @Test @DisplayName("should update a record")
    fun testUpdate() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()

        val entity = ItemEntity()
        entity.id = id
        entity.content = "dbContent"
        entity.contentType = "dbContentType"

        val dto = ItemGetDto(id, null, "content", "contentType", null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))
        `when`(itemRepo.save(entity)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemUpdateDto = ItemPostDto("content", "contentType")

        val itemService = ItemService(itemRepo, converterService, eventService)
        val returned = itemService.update(id, itemUpdateDto).get()

        assertEquals(dto, returned)
    }

    @Test @DisplayName("should rank item up")
    fun testRankUp() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()

        val entity = ItemEntity()
        entity.id = id
        entity.content = "dbContent"
        entity.contentType = "dbContentType"

        val dto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.rankUp(id)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemService = ItemService(itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.UP)

        assertEquals(dto, returned)
    }

    @Test @DisplayName("should rank item down")
    fun testRankDown() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()

        val entity = ItemEntity()
        entity.id = id
        entity.content = "dbContent"
        entity.contentType = "dbContentType"

        val dto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.rankDown(id)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemService = ItemService(itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.DOWN)

        assertEquals(dto, returned)
    }

    @Test @DisplayName("should rank item top")
    fun testRankTop() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()

        val entity = ItemEntity()
        entity.id = id
        entity.content = "dbContent"
        entity.contentType = "dbContentType"

        val dto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.rankTop(id)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemService = ItemService(itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.TOP)

        assertEquals(dto, returned)
    }

    @Test @DisplayName("should rank item bottom")
    fun testRankBottom() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()

        val entity = ItemEntity()
        entity.id = id
        entity.content = "dbContent"
        entity.contentType = "dbContentType"

        val dto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.rankBottom(id)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemService = ItemService(itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.BOTTOM)

        assertEquals(dto, returned)
    }

    @Test @DisplayName("should emit an update event")
    fun testUpdateEvent() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()

        val entity = ItemEntity()
        entity.id = id
        entity.content = "dbContent"
        entity.contentType = "dbContentType"

        val dto = ItemGetDto(id, null, "content", "contentType", null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))
        `when`(itemRepo.save(entity)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemUpdateDto = ItemPostDto("content", "contentType")

        val itemService = ItemService(itemRepo, converterService, eventService)
        itemService.update(id, itemUpdateDto).get()

        verify(eventService).update(id, itemUpdateDto)
    }

    @Test @DisplayName("should emit a rank event")
    fun testRankEvent() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()

        val entity = ItemEntity()
        entity.id = id
        entity.content = "dbContent"
        entity.contentType = "dbContentType"
        entity.rank = 1

        val dto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.rankTop(id)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemService = ItemService(itemRepo, converterService, eventService)
        itemService.rank(id, RankOperation.TOP)

        verify(eventService).rank(id, RankOperation.TOP, 1)
    }
}