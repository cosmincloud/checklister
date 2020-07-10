@file:UseSerializers(UUIDSerializer::class, OffsetDateTimeSerializer::class)
package cloud.cosmin.checklister.lib.dto

import cloud.cosmin.checklister.lib.dto.ParsingFunctions.parseInt
import cloud.cosmin.checklister.lib.dto.ParsingFunctions.parseUUID
import cloud.cosmin.checklister.lib.dto.internal.OffsetDateTimeSerializer
import cloud.cosmin.checklister.lib.dto.internal.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.HashMap
import java.util.UUID

@Serializable
data class ItemGetDto(val id: UUID?,
                      val list: UUID?,
                      val content: String?,
                      val contentType: String?,
                      val rank: Int?,
                      val createdAt: OffsetDateTime,
                      val lastModified: OffsetDateTime) : Mappable {
    companion object {
        fun fromMap(map: Map<String, String>): ItemGetDto {
            val id = parseUUID(map.get("id"))
            val list = parseUUID(map.get("list"))
            val content = map.get("content")
            val contentType = map.get("contentType")
            val rank = parseInt(map.get("rank"))
            val createdAt = when(val raw = map.get("createdAt")) {
                null -> OffsetDateTime.parse("1970-01-01T00:00:00Z")
                else -> OffsetDateTime.parse(raw)
            }
            val modifiedAt = when(val raw = map.get("lastModified")) {
                null -> OffsetDateTime.parse("1970-01-01T00:00:00Z")
                else -> OffsetDateTime.parse(raw)
            }
            return ItemGetDto(id, list, content, contentType, rank, createdAt, modifiedAt)
        }
    }

    override fun toMap(): Map<String, String> {
        val map = HashMap<String, String>()
        if (id != null) {
            map.put("id", id.toString())
        }
        if (list != null) {
            map.put("list", list.toString())
        }
        if (content != null) {
            map.put("content", content)
        }
        if (contentType != null) {
            map.put("contentType", contentType)
        }
        if (rank != null) {
            map.put("rank", rank.toString())
        }

        map.put("createdAt", createdAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
        map.put("lastModified", lastModified.format(DateTimeFormatter.ISO_ZONED_DATE_TIME))

        return map
    }
}