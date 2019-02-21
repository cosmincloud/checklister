package cloud.cosmin.checklister.service

import cloud.cosmin.checklister.dao.ListEntity
import cloud.cosmin.checklister.dto.ListGetDto
import cloud.cosmin.checklister.dto.ListPostDto
import cloud.cosmin.checklister.repo.ListRepo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

@DisplayName("ListService")
internal class ListServiceTest {
    @Test @DisplayName("should save lists")
    fun test01() {
        val listRepo = mock(ListRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val uuidService = mock(UuidService::class.java)
        val service = ListService(listRepo, converterService, uuidService)

        val uuid = UUID.randomUUID()
        val entity = ListEntity()
        entity.id = uuid
        entity.title = "title"

        val savedEntity = ListEntity()
        savedEntity.id = UUID.randomUUID()
        savedEntity.title = "title"

        val listGetDto = ListGetDto(savedEntity.id, "title")

        `when`(uuidService.get()).thenReturn(uuid)
        `when`(listRepo.save(entity)).thenReturn(savedEntity)
        `when`(converterService.listDto(savedEntity)).thenReturn(listGetDto)

        val dto = ListPostDto("title")
        val createdDto = service.create(dto)

        verify(listRepo).save(entity)
        assertEquals(savedEntity.id, createdDto.id)
        assertEquals("title", createdDto.title)
    }

    @Test @DisplayName("should update lists")
    fun test02() {
        val listRepo = mock(ListRepo::class.java)
        val converterService = mock(ConverterService::class.java)
        val uuidService = mock(UuidService::class.java)
        val service = ListService(listRepo, converterService, uuidService)

        val listId = UUID.randomUUID()

        val dbList = ListEntity()
        dbList.id = UUID.randomUUID()
        dbList.title = "dbTitle"

        val updatedList = ListEntity()
        updatedList.id = dbList.id
        updatedList.title = "newTitle"

        val listGetDto = ListGetDto(updatedList.id, "newTitle")

        `when`(listRepo.findById(listId)).thenReturn(Optional.of(dbList))
        `when`(listRepo.save(updatedList)).thenReturn(updatedList)
        `when`(converterService.listDto(updatedList)).thenReturn(listGetDto)

        val listPostDto = ListPostDto("newTitle")

        val updatedDto = service.update(listId, listPostDto)

        verify(listRepo).save(updatedList)
        assertEquals(updatedList.id, updatedDto.get().id)
        assertEquals("newTitle", updatedDto.get().title)
    }
}