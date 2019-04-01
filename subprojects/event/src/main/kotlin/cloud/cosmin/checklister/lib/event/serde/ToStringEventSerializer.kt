package cloud.cosmin.checklister.lib.event.serde

import cloud.cosmin.checklister.lib.event.Event

class ToStringEventSerializer : EventSerializer {
    override fun serialize(event: Event): ByteArray {
        return event.toString().toByteArray()
    }
}