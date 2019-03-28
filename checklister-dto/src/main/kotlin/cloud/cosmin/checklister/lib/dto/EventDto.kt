package cloud.cosmin.checklister.lib.dto

import java.util.UUID

data class EventDto(val id: UUID,
                    val type: String,
                    val event: String)