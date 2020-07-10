@file:UseSerializers(UUIDSerializer::class)
package cloud.cosmin.checklister.lib.dto

import cloud.cosmin.checklister.lib.dto.internal.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
data class ListWithItemsDto(
        var id: UUID?,
        var title: String?,
        var items: MutableList<ItemGetDto>?
)