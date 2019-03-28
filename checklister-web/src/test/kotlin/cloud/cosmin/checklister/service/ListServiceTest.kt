package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ListEntity
import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.dto.ListPostDto
import cloud.cosmin.checklister.repo.ListRepo
import cloud.cosmin.checklister.service.event.ListEventService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

@DisplayName("ListService")
internal class ListServiceTest {
    val listRepo = mock(ListRepo::class.java)
    val converterService = ConverterService()
    val uuidService = mock(UuidService::class.java)
    val listEventService = mock(ListEventService::class.java)

    fun createListService(): ListService {
        return ListService(listRepo, converterService, uuidService, listEventService)
    }

    @BeforeEach
    fun setUp() {
        reset(listRepo, uuidService, listEventService)
    }

    @Test @DisplayName("should update lists")
    fun test01() {
        val service = createListService()

        val uuid = UUID.randomUUID()
        val entity = ListEntity()
        entity.id = uuid
        entity.title = "title"

        val savedEntity = ListEntity()
        savedEntity.id = UUID.randomUUID()
        savedEntity.title = "title"

        `when`(uuidService.get()).thenReturn(uuid)
        `when`(listRepo.save(entity)).thenReturn(savedEntity)

        val dto = ListPostDto("title")
        val createdDto = service.create(dto)

        verify(listRepo).save(entity)
        assertEquals(savedEntity.id, createdDto.id)
        assertEquals("title", createdDto.title)
    }

    @Test @DisplayName("should update lists")
    fun test02() {
        val service = createListService()

        val listId = UUID.randomUUID()

        val dbList = ListEntity()
        dbList.id = UUID.randomUUID()
        dbList.title = "dbTitle"

        val updatedList = ListEntity()
        updatedList.id = dbList.id
        updatedList.title = "newTitle"

        `when`(listRepo.findById(listId)).thenReturn(Optional.of(dbList))
        `when`(listRepo.save(updatedList)).thenReturn(updatedList)

        val listPostDto = ListPostDto("newTitle")

        val updatedDto = service.update(listId, listPostDto)

        verify(listRepo).save(updatedList)
        assertEquals(updatedList.id, updatedDto.get().id)
        assertEquals("newTitle", updatedDto.get().title)
    }

    @Test @DisplayName("should emit update events")
    fun test03() {
        val service = createListService()

        val uuid = UUID.randomUUID()
        `when`(uuidService.get()).thenReturn(uuid)

        val listEntity = ListEntity()
        listEntity.id = uuid
        listEntity.title = "listTitle"
        `when`(listRepo.save(listEntity)).thenReturn(listEntity)

        val post = ListPostDto("listTitle")
        service.create(post)

        val listGetDto = ListGetDto(uuid, "listTitle")
        verify(listEventService).create(listGetDto)
    }

    @Test @DisplayName("should emit update events")
    fun test04() {
        val service = createListService()

        val uuid = UUID.randomUUID()

        val currentEntity = ListEntity()
        currentEntity.id = uuid
        currentEntity.title = "oldTitle"
        `when`(listRepo.findById(uuid)).thenReturn(Optional.of(currentEntity))

        val updatedEntity = ListEntity()
        updatedEntity.id = uuid
        updatedEntity.title = "newTitle"
        `when`(listRepo.save(updatedEntity)).thenReturn(updatedEntity)

        val post = ListPostDto("newTitle")
        service.update(uuid, post)

        val listBeforeDto = ListGetDto(currentEntity.id, "oldTitle")
        val listAfterDto = ListGetDto(updatedEntity.id, "newTitle")
        verify(listEventService).update(listBeforeDto, listAfterDto)
    }
}