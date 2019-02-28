package cloud.cosmin.checklister.dto

import cloud.cosmin.checklister.lib.dto.ListPostDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ListPostDto")
internal class ListPostDtoTest {
    @Test @DisplayName("should be de-serialized by ObjectMapper")
    fun canBeSerialized() {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        val dto = objectMapper.readValue<ListPostDto>("""
            {
              "title": "mytitle"
            }
        """.trimIndent(), ListPostDto::class.java)
        assertEquals("mytitle", dto.title)
    }
}