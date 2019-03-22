package cloud.cosmin.checklister.lib.dto

import java.util.*

data class ListGetDto(
        val id: UUID?,
        val title: String?
) : Mappable {
    companion object {
        fun fromMap(map: Map<String, String>): ListGetDto {
            val id = ParsingFunctions.parseUUID(map.get("id"))
            val title = map.get("title")
            return ListGetDto(id, title)
        }
    }

    override fun toMap(): Map<String, String> {
        val map = HashMap<String, String>()
        if (id != null) {
            map.put("id", id.toString())
        }
        if (title != null) {
            map.put("title", title)
        }
        return map
    }
}
