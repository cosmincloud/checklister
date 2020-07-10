package cloud.cosmin.checklister.lib.dto

/**
 * A light weight interface to indicate that an object can be
 * represented as a map of fields with values.
 */
interface Mappable {
    fun toMap(): Map<String, String>
}