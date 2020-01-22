package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.entity.ItemEntity
import cloud.cosmin.checklister.entity.ListEntity
import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.dto.ItemPostDto
import cloud.cosmin.checklister.lib.event.model.RankOperation
import cloud.cosmin.checklister.repository.ItemRepository
import cloud.cosmin.checklister.repository.ListRepository
import cloud.cosmin.checklister.service.event.ItemEventService
import org.junit.Ignore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.time.OffsetDateTime
import java.util.*

@DisplayName("ItemService")
internal class ItemServiceTest {
    private val listRepo = mock(ListRepository::class.java)
    private val itemRepo = mock(ItemRepository::class.java)
    private val converterService = mock(ConverterService::class.java)
    private val eventService = mock(ItemEventService::class.java)
    private val date = OffsetDateTime.now()

    @BeforeEach
    fun setUp() {
        reset(listRepo, itemRepo, converterService, eventService)
    }

    @Test @DisplayName("should return an item from the database")
    fun testGet() {
        val id = UUID.randomUUID()
        val entity = mock(ItemEntity::class.java)
        val dto = ItemGetDto(null, null, null, null, null, date, date)

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

        val dto = ItemGetDto(id, listId, "content", "contentType", null, date, date)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))
        `when`(itemRepo.save(entity)).thenReturn(entity)
        `when`(converterService.itemDto(entity)).thenReturn(dto)

        val itemUpdateDto = ItemPostDto(null, "content", "contentType")

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.update(id, itemUpdateDto)

        assertEquals(dto, returned)
    }

    @Test @DisplayName("should rank item up")
    @Disabled
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

        val beforeDto = ItemGetDto(id, null, null, null, null, date, date)
        val afterDto = ItemGetDto(id, null, null, null, null, date, date)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankUp(id, date)).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.UP)

        assertEquals(afterDto, returned)
    }

    @Test @DisplayName("should rank item down")
    @Disabled
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

        val beforeDto = ItemGetDto(id, null, null, null, null, date, date)
        val afterDto = ItemGetDto(id, null, null, null, null, date, date)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankDown(ArgumentMatchers.eq(id), ArgumentMatchers.any())).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.DOWN)

        assertEquals(afterDto, returned)
    }

    @Test @DisplayName("should rank item top")
    @Disabled
    fun testRankTop() {
        val itemRepo = mock(ItemRepository::class.java)
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

        val beforeDto = ItemGetDto(id, null, null, null, null, date, date)
        val afterDto = ItemGetDto(id, null, null, null, null, date, date)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankTop(ArgumentMatchers.eq(id), ArgumentMatchers.any())).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.TOP)

        assertEquals(afterDto, returned)
    }

    @Test @DisplayName("should rank item bottom")
    @Disabled
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

        val beforeDto = ItemGetDto(id, null, null, null, null, date, date)
        val afterDto = ItemGetDto(id, null, null, null, null, date, date)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankBottom(ArgumentMatchers.eq(id), ArgumentMatchers.any())).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        val returned = itemService.rank(id, RankOperation.BOTTOM)

        assertEquals(afterDto, returned)
    }

    @Test @DisplayName("should emit an update event")
    @Ignore
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

        val beforeDto = ItemGetDto(id, listId, "content", "contentType", null, date, date)
        val afterDto = ItemGetDto(id, listId, "content", "contentType", null, date, date)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.save(before)).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemUpdateDto = ItemPostDto(null, "content", "contentType")

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        itemService.update(id, itemUpdateDto)

        verify(eventService).update(beforeDto, afterDto)
    }

    @Test @DisplayName("should emit a rank event")
    @Disabled
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

        val beforeDto = ItemGetDto(id, null, null, null, null, date, date)
        val afterDto = ItemGetDto(id, null, null, null, null, date, date)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(before))
        `when`(itemRepo.rankTop(ArgumentMatchers.eq<UUID>(id), ArgumentMatchers.any())).thenReturn(after)
        `when`(converterService.itemDto(before)).thenReturn(beforeDto)
        `when`(converterService.itemDto(after)).thenReturn(afterDto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        itemService.rank(id, RankOperation.TOP)

        verify(eventService).rank(RankOperation.TOP, beforeDto, afterDto)
    }

    @Test @DisplayName("should emit a create event")
    @Ignore
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

        val dto = ItemGetDto(id, null, null, null, null, date, date)

        `when`(listRepo.findById(listId)).thenReturn(Optional.of(list))
        `when`(itemRepo.save(entity)).thenReturn(savedEntity)
        `when`(converterService.itemDto(savedEntity)).thenReturn(dto)

        val itemService = ItemService(listRepo, itemRepo, converterService, eventService)
        itemService.create(ItemPostDto(listId, "dbContent", "dbContentType"))

        verify(eventService).create(dto)
    }
}