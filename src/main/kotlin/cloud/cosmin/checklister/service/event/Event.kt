package cloud.cosmin.checklister.service.event

interface Event {
    fun serialize(): ByteArray
}