package cloud.cosmin.checklister.lib.dto.internal

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.util.*

class UUIDSerializerTest : WordSpec({
    "UUIDSerializer" should {
        "should read back its own serialization" {
            val json = Json.Default
            val uuid = UUID.fromString("fb54a699-8d73-4b41-87bb-32b20ec15618")
            val jsonData = json.encodeToString(UUIDSerializer, uuid)
            jsonData.shouldBe("\"fb54a699-8d73-4b41-87bb-32b20ec15618\"")
            val newUUID = json.decodeFromString(UUIDSerializer, jsonData)
            uuid.shouldBeEqualComparingTo(newUUID)
        }
    }
})
