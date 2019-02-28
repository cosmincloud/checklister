package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dao.ListEntity
import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.dto.ItemPostDto
import cloud.cosmin.checklister.lib.event.model.RankOperation
import cloud.cosmin.checklister.repo.ItemRepo
import cloud.cosmin.checklister.repo.ListRepo
import cloud.cosmin.checklister.service.event.ItemEventService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

@DisplayName("ItemService")
internal class ItemServiceTest {
    private val listRepo = mock(ListRepo::class.java)
    private val itemRepo = mock(ItemRepo::class.java)
    private val converterService = mock(ConverterService::class.java)
    private val eventService = mock(ItemEventService::class.java)

    @BeforeEach
    fun setUp() {
        reset(listRepo, itemRepo, converterService, eventService)
    }

    @Test @DisplayName("should return an item from the database")
    fun testGet() {
        val id = UUID.randomUUID()
        val entity = mock(ItemEntity::class.java)
        val dto = ItemGetDto(null, null, null, null, null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.findById(id).get()
        assertEquals(dto, returned)

        verify(itemRepo).findById(id)
        verify(converterService).itemDto(entity)
    }

    @Test @DisplayName("should return nothing when an entity not found in the DB")
    fun testGetNotFound() {
        val id = UUID.randomUUID()

        `when`(itemRepo.findById(id)).thenReturn(Optional.empty())

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.findById(id)
        assertTrue(returned.isEmpty)

        verify(itemRepo).findById(id)
        verifyZeroInteractions(converterService)
    }

    @Test @DisplayName("should update an item")
    fun testUpdate() {
        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()

        val entity = ItemEntity()
        entity.id = id
        entity.content = "dbContent"
        entity.contentType = "dbContentType"

        val dto = ItemGetDto(id, listId, "content", "contentType", null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))
        `when`(itemRepo.save(entity)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemUpdateDto = ItemPostDto(listId, "content", "contentType")

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.update(id, itemUpdateDto)

        assertEquals(dto, returned)
    }

    @Test @DisplayName("should rank item up")
    fun testRankUp() {
        val id = UUID.randomUUID()

        val before = ItemEntity()
        before.id = id
        before.content = "dbContent"
        before.contentType = "dbContentType"

        val after = ItemEntity()
        after.id = id
        after.content = "dbContent"
        after.contentType = "dbContentType"

        val beforeDto = ItemGetDto(id, null, null, null, null)
        val afterDto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankUp(id)).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.UP)

        assertEquals(afterDto, returned)
    }

    @Test @DisplayName("should rank item down")
    fun testRankDown() {
        val id = UUID.randomUUID()

        val before = ItemEntity()
        before.id = id
        before.content = "dbContent"
        before.contentType = "dbContentType"

        val after = ItemEntity()
        after.id = id
        after.content = "dbContent"
        after.contentType = "dbContentType"

        val beforeDto = ItemGetDto(id, null, null, null, null)
        val afterDto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankDown(id)).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.DOWN)

        assertEquals(afterDto, returned)
    }

    @Test @DisplayName("should rank item top")
    fun testRankTop() {
        val itemRepo = mock(ItemRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val eventService = mock(ItemEventService::class.java)

        val id = UUID.randomUUID()

        val before = ItemEntity()
        before.id = id
        before.content = "dbContent"
        before.contentType = "dbContentType"

        val after = ItemEntity()
        after.id = id
        after.content = "dbContent"
        after.contentType = "dbContentType"

        val beforeDto = ItemGetDto(id, null, null, null, null)
        val afterDto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankTop(id)).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.TOP)

        assertEquals(afterDto, returned)
    }

    @Test @DisplayName("should rank item bottom")
    fun testRankBottom() {
        val id = UUID.randomUUID()

        val before = ItemEntity()
        before.id = id
        before.content = "dbContent"
        before.contentType = "dbContentType"

        val after = ItemEntity()
        after.id = id
        after.content = "dbContent"
        after.contentType = "dbContentType"

        val beforeDto = ItemGetDto(id, null, null, null, null)
        val afterDto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankBottom(id)).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.BOTTOM)

        assertEquals(afterDto, returned)
    }

    @Test @DisplayName("should emit an update event")
    fun testUpdateEvent() {
        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()

        val before = ItemEntity()
        before.id = id
        before.content = "dbContent"
        before.contentType = "dbContentType"

        val after = ItemEntity()
        after.id = id
        after.content = "dbContent"
        after.contentType = "dbContentType"

        val beforeDto = ItemGetDto(id, listId, "content", "contentType", null)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.save(before)).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemUpdateDto = ItemPostDto(listId, "content", "contentType")

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        itemService.update(id, itemUpdateDto)

        verify(eventService).update(beforeDto, afterDto)
    }

    @Test @DisplayName("should emit a rank event")
    fun testRankEvent() {
        val id = UUID.randomUUID()

        val before = ItemEntity()
        before.id = id
        before.content = "dbContent"
        before.contentType = "dbContentType"
        before.rank = 50

        val after = ItemEntity()
        after.id = id
        after.content = "dbContent"
        after.contentType = "dbContentType"
        after.rank = 1

        val beforeDto = ItemGetDto(id, null, null, null, null)
        val afterDto = ItemGetDto(id, null, null, null, null)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankTop(id)).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        itemService.rank(id, RankOperation.TOP)

        verify(eventService).rank(RankOperation.TOP, beforeDto, afterDto)
    }

    @Test @DisplayName("should emit a create event")
    fun testCreateEvent() {
        val id = UUID.randomUUID()
        val listId = UUID.randomUUID()

        val list = ListEntity()
        list.id = listId
        list.title = "title"
        list.items = listOf()

        val entity = ItemEntity()
        entity.list = list
        entity.content = "dbContent"
        entity.contentType = "dbContentType"
        entity.rank = 2

        val savedEntity = ItemEntity()
        savedEntity.list = list
        savedEntity.content = "dbContent"
        savedEntity.contentType = "dbContentType"
        savedEntity.rank = 2
        savedEntity.id = id

        val dto = ItemGetDto(id, null, null, null, null)

        `when`(listRepo.findById(listId)).thenReturn(Optional.of(list))
        `when`(itemRepo.save(entity)).thenReturn(savedEntity)
        `when`(converterService.itemDto(savedEntity)).thenReturn(dto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        itemService.create(ItemPostDto(listId, "dbContent", "dbContentType"))

        verify(eventService).create(dto)
    }
}