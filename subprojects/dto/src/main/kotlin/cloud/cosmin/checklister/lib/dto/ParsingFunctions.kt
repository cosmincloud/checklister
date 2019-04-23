package cloud.cosmin.checklister.lib.dto

import java.time.OffsetDateTime
import java.util.*

object ParsingFunctions {
    fun parseUUID(str: String?): UUID? {
        if (str != null) {
            return UUID.fromString(str)
        } else {
            return null
        }
    }

    fun parseInt(str: String?): Int? {
        if (str != null) {
            return Integer.parseInt(str)
        } else {
            return null
        }
    }

    fun parseOffsetdDateTime(str: String?): OffsetDateTime? {
        return OffsetDateTime.parse(str)
    }
}