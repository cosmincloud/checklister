package cloud.cosmin.checklister.lib.dto

import java.util.*

data class ItemGetDto(var id: UUID?,
                      var list: UUID?,
                      var content: String?,
                      var contentType: String?,
                      var rank: Int?)