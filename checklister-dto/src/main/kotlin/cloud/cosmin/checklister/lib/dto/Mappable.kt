package cloud.cosmin.checklister.lib.dto

interface Mappable {
    fun toMap(): Map<String, String>
}