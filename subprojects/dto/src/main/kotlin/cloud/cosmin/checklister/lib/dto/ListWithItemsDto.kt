package cloud.cosmin.checklister.lib.dto

import java.util.*

data class ListWithItemsDto(
        var id: UUID?,
        var title: String?,
        var items: MutableList<ItemGetDto>?
)