package cloud.cosmin.checklister.it

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.dto.ItemPostDto
import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.dto.ListPostDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.jackson.responseObject
import io.kotlintest.shouldBe
import java.util.UUID

object TestHelper {
    val baseUrl = "http://localhost:8180/api/v1"

    val mapper = ObjectMapper()
            .registerKotlinModule()
            .registerModule(ParameterNamesModule())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())

    fun ListPostDto.toJson(): String {
        return mapper.writeValueAsString(this)
    }

    fun ItemPostDto.toJson(): String {
        return mapper.writeValueAsString(this)
    }

    fun ListPostDto.post(): ListGetDto {
        val (_, _, listResult) = Fuel.post("$baseUrl/list")
                .jsonBody(this.toJson())
                .responseObject<ListGetDto>(mapper)
        return listResult.get()
    }

    fun ItemPostDto.post(): ItemGetDto {
        val (_, _, updateItemResult) = Fuel.post("$baseUrl/item")
                .jsonBody(this.toJson())
                .responseObject<ItemGetDto>(mapper)
        return updateItemResult.get()
    }

    fun ItemPostDto.put(id: UUID): ItemGetDto {
        val (_, _, updateItemResult) = Fuel.put("$baseUrl/item/$id")
                .jsonBody(this.toJson())
                .responseObject<ItemGetDto>(mapper)
        return updateItemResult.get()
    }
}