package cloud.cosmin.checklister.lib.dto

import java.util.*

data class ItemPostDto(
        var list: UUID?,
        var content: String?,
        var contentType: String?
)