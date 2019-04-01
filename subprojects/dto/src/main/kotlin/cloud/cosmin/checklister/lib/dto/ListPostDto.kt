package cloud.cosmin.checklister.lib.dto

import java.util.*

data class ListPostDto(
        val title: String
) : Mappable {
    companion object {
        fun fromMap(map: Map<String, String>): ListPostDto {
            val title = map.get("title")!!
            return ListPostDto(title)
        }
    }

    override fun toMap(): Map<String, String> {
        val map = HashMap<String, String>()
        map.put("title", title)
        return map
    }
}
