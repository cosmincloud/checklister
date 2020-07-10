@file:UseSerializers(UUIDSerializer::class)
package cloud.cosmin.checklister.lib.dto

import cloud.cosmin.checklister.lib.dto.ParsingFunctions.parseUUID
import cloud.cosmin.checklister.lib.dto.internal.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
data class ItemPostDto(
        val list: UUID?,
        val content: String?,
        val contentType: String?
) : Mappable{
    companion object {
        fun fromMap(map: Map<String, String>): ItemPostDto {
            val list = parseUUID(map.get("list"))
            val content = map.get("content")
            val contentType = map.get("contentType")
            return ItemPostDto(list, content, contentType)
        }
    }

    override fun toMap(): Map<String, String> {
        val map = HashMap<String, String>()
        if (list != null) {
            map.put("list", list.toString())
        }
        if (content != null) {
            map.put("content", content)
        }
        if (contentType != null) {
            map.put("contentType", contentType)
        }
        return map
    }
}