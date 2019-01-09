package cloud.cosmin.checklister.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class ItemGetDto(var id: UUID?,
                      var list: UUID?,
                      var content: String?,
                      var contentType: String?,
                      var rank: Int?)

data class ItemPostDto(
        @JsonProperty var content: String?,
        @JsonProperty var contentType: String?
)

data class ItemUpdateDto(
        @JsonProperty var id: UUID,
        @JsonProperty var content: String?,
        @JsonProperty var contentType: String?
)

data class ListGetDto(
    var id: UUID?,
    var title: String?
)

data class ListPostDto(
    @JsonProperty var title: String
)

data class ListWithItemsDto(
    var id: UUID?,
    var title: String?,
    var items: MutableList<ItemGetDto>?
)