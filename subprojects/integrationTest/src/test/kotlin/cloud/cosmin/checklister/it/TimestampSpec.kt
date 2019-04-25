package cloud.cosmin.checklister.it

import cloud.cosmin.checklister.lib.dto.ItemGetDto
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.jackson.responseObject
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.string.shouldMatch
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import cloud.cosmin.checklister.it.TestHelper.baseUrl
import cloud.cosmin.checklister.it.TestHelper.mapper
import cloud.cosmin.checklister.it.TestHelper.toJson
import cloud.cosmin.checklister.it.TestHelper.post
import cloud.cosmin.checklister.lib.dto.ItemPostDto
import cloud.cosmin.checklister.lib.dto.ListPostDto
import com.github.kittinunf.fuel.core.extensions.jsonBody

class TimestampSpec : WordSpec({
    val timestampRegex = Regex("[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{6}Z")

    "Checklister" When {
        "creating an item" should {
            "return the lastModified field" {
                val list = ListPostDto("test list").post()
                list.title.shouldBe("test list")

                val item = ItemPostDto(list.id, "new content", "new type").post()
                item.createdAt.toString().shouldMatch(timestampRegex)
                item.lastModified.toString().shouldMatch(timestampRegex)

                val (_, _, updateItemResult) = Fuel.put("$baseUrl/item/${item.id}")
                        .jsonBody(ItemPostDto(list.id, "new content", "new type").toJson())
                        .responseObject<ItemGetDto>(mapper)

                val updatedItem = updateItemResult.get()
                updatedItem.createdAt.shouldBe(item.createdAt)
                updatedItem.lastModified.isAfter(item.lastModified).shouldBeTrue()
            }
        }
    }
})