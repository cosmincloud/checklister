package cloud.cosmin.checklister.lib.dto.internal

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import java.util.*

/**
 * A kotlinx.serialization serializer for java.util.UUID.
 */
@Serializer(forClass = UUID::class)
object UUIDSerializer: KSerializer<UUID> {
    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}