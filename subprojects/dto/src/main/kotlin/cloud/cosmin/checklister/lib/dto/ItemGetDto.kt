package cloud.cosmin.checklister.lib.dto

import cloud.cosmin.checklister.lib.dto.ParsingFunctions.parseInt
import cloud.cosmin.checklister.lib.dto.ParsingFunctions.parseUUID
import java.util.*

data class ItemGetDto(val id: UUID?,
                      val list: UUID?,
                      val content: String?,
                      val contentType: String?,
                      val rank: Int?) : Mappable {
    companion object {
        fun fromMap(map: Map<String, String>): ItemGetDto {
            val id = parseUUID(map.get("id"))
            val list = parseUUID(map.get("list"))
            val content = map.get("content")
            val contentType = map.get("contentType")
            val rank = parseInt(map.get("rank"))
            return ItemGetDto(id, list, content, contentType, rank)
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
        return map
    }
}