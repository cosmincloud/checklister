package cloud.cosmin.checklister.lib.dto.internal

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * A kotlinx.serialization serializer for java.time.OffsetDateTime.
 */
@Serializer(forClass = OffsetDateTime::class)
object OffsetDateTimeSerializer: KSerializer<OffsetDateTime> {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        val parsed = formatter.parse(decoder.decodeString())
        return OffsetDateTime.from(parsed)
    }
}