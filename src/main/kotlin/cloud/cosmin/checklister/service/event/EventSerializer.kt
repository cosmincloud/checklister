package cloud.cosmin.checklister.service.event

interface EventSerializer {
    fun serialize(event: Event): ByteArray
}