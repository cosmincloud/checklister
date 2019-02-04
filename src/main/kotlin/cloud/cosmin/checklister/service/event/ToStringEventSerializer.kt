package cloud.cosmin.checklister.service.event

class ToStringEventSerializer : EventSerializer {
    override fun serialize(event: Event): ByteArray {
        return event.toString().toByteArray(Charsets.UTF_8)
    }
}