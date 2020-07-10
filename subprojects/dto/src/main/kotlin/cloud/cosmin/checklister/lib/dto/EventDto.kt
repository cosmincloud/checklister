@file:UseSerializers(UUIDSerializer::class)
package cloud.cosmin.checklister.lib.dto

import cloud.cosmin.checklister.lib.dto.internal.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
data class EventDto(val id: UUID,
                    val type: String,
                    val event: String)