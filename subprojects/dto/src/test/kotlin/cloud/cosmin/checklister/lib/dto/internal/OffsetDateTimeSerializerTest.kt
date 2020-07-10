package cloud.cosmin.checklister.lib.dto.internal

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.time.OffsetDateTime

class OffsetDateTimeSerializerTest : WordSpec({
    "OffsetDateTimeSerializer" should {
        "should read back its own serialization" {
            val json = Json(JsonConfiguration.Stable)
            val offsetDateTime = OffsetDateTime.parse("2020-07-10T01:00:00+05:00")
            val jsonData = json.stringify(OffsetDateTimeSerializer, offsetDateTime)
            jsonData.shouldBe("\"2020-07-10T01:00:00+05:00\"")
            val newOffsetDateTime = json.parse(OffsetDateTimeSerializer, jsonData)
            offsetDateTime.shouldBeEqualComparingTo(newOffsetDateTime)
        }
    }
})