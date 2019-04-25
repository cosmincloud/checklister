package cloud.cosmin.checklister.it

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import cloud.cosmin.checklister.lib.dto.ListGetDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.jackson.responseObject
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.string.shouldMatch
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class TimestampSpec : WordSpec({
    val baseUrl = "http://localhost:8180/api/v1"
    val contentType = "Content-Type" to "application/json"

    val mapper = ObjectMapper()
            .registerKotlinModule()
            .registerModule(ParameterNamesModule())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())

    val timestampRegex = Regex("[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{6}Z")

    "Checklister" When {
        "creating an item" should {
            "return the lastModified field" {
                val (_, _, listResult) = Fuel.post("$baseUrl/list")
                        .header(contentType)
                        .body("{\"title\":\"test list\"}")
                        .responseObject<ListGetDto>(mapper)

                val list = listResult.get()
                list.title.shouldBe("test list")

                val (_, _, createItemResult) = Fuel.post("$baseUrl/item")
                        .header(contentType)
                        .body("""
                            {
                              "list": "${list.id}",
                              "content": "content",
                              "contentType": "contentType"
                            }
                        """.trimIndent())
                        .responseObject<ItemGetDto>(mapper)

                val item = createItemResult.get()
                item.createdAt.toString().shouldMatch(timestampRegex)
                item.lastModified.toString().shouldMatch(timestampRegex)

                val (_, _, updateItemResult) = Fuel.put("$baseUrl/item/${item.id}")
                        .header(contentType)
                        .body("""
                            {
                              "list": "${list.id}",
                              "content": "content1",
                              "contentType": "contentType1"
                            }
                        """.trimIndent())
                        .responseObject<ItemGetDto>(mapper)

                val updatedItem = updateItemResult.get()
                updatedItem.createdAt.shouldBe(item.createdAt)
                updatedItem.lastModified.isAfter(item.lastModified).shouldBeTrue()
            }
        }
    }
})